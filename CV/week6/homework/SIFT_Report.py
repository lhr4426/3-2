import cv2
import numpy as np
from cv2 import KeyPoint

def find_local_maxima(src, ksize):
    (h, w) = src.shape
    pad_img = np.zeros((h + ksize, w + ksize))
    pad_img[ksize // 2:h + ksize // 2, ksize // 2:w + ksize // 2] = src
    dst = np.zeros((h, w))

    for row in range(h):
        for col in range(w):
            max_val = np.max(pad_img[row: row + ksize, col:col + ksize])
            if max_val == 0:
                continue
            if src[row, col] == max_val:
                dst[row, col] = src[row, col]

    return dst


def my_padding(src, filter):
    (h, w) = src.shape
    if isinstance(filter, tuple):
        (h_pad, w_pad) = filter
    else:
        (h_pad, w_pad) = filter.shape
    h_pad = h_pad // 2
    w_pad = w_pad // 2
    padding_img = np.zeros((h + h_pad * 2, w + w_pad * 2))
    padding_img[h_pad:h + h_pad, w_pad:w + w_pad] = src

    # repetition padding
    # up
    padding_img[:h_pad, w_pad:w_pad + w] = src[0, :]
    # down
    padding_img[h_pad + h:, w_pad:w_pad + w] = src[h - 1, :]
    # left
    padding_img[:, :w_pad] = padding_img[:, w_pad:w_pad + 1]
    # right
    padding_img[:, w_pad + w:] = padding_img[:, w_pad + w - 1:w_pad + w]

    return padding_img

def my_filtering(src, filter):
    (h, w) = src.shape
    (f_h, f_w) = filter.shape

    # filter 확인
    # print('<filter>')
    # print(filter)

    # 직접 구현한 my_padding 함수를 이용
    pad_img = my_padding(src, filter)

    dst = np.zeros((h, w))
    for row in range(h):
        for col in range(w):
            dst[row, col] = np.sum(pad_img[row:row + f_h, col:col + f_w] * filter)

    return dst

def get_my_sobel():
    sobel_x = np.dot(np.array([[1], [2], [1]]), np.array([[-1, 0, 1]]))
    sobel_y = np.dot(np.array([[-1], [0], [1]]), np.array([[1, 2, 1]]))
    return sobel_x, sobel_y

def calc_derivatives(src):
    # calculate Ix, Iy
    sobel_x, sobel_y = get_my_sobel()
    Ix = my_filtering(src, sobel_x)
    Iy = my_filtering(src, sobel_y)
    return Ix, Iy

def SIFT(src):
    gray = cv2.cvtColor(src, cv2.COLOR_BGR2GRAY).astype(np.float32)

    print("get keypoint")
    dst = cv2.cornerHarris(gray, 3, 3, 0.04)
    dst[dst < 0.01 * dst.max()] = 0
    dst = find_local_maxima(dst, 21)
    dst = dst / dst.max()
    
    #harris corner에서 keypoint를 추출
    y, x = np.nonzero(dst)

    keypoints = []
    for i in range(len(x)):
        # x, y, size, angle, response, octave, class_id
        pt_x = int(x[i]) #point x
        pt_y = int(y[i]) #point y
        size = None
        key_angle = -1.
        response = dst[y[i], x[i]] # keypoint에서 harris corner의 측정값
        octave = 0 # octave는 scale 변화를 확인하기 위한 값 (현재 과제에서는 사용안함)
        class_id = -1
        keypoints.append(KeyPoint(pt_x, pt_y, size, key_angle, response, octave, class_id))

    print('get Ix and Iy...')
    Ix, Iy = calc_derivatives(gray)

    print('calculate angle and magnitude')
    # magnitude / orientation 계산
    magnitude = np.sqrt(Ix ** 2 + Iy ** 2)
    angle = np.rad2deg(np.arctan2(Iy, Ix))  ## -180 ~ 180으로 표현
    angle = (angle + 360) % 360  ## 0 ~ 360으로 표현

    # keypoint 방향
    print('calculate orientation assignment')
    for i in range(len(keypoints)):
        x, y = keypoints[i].pt
        orient_hist = np.zeros(36, ) #point의 방향을 저장
        for row in range(-8, 8):
            for col in range(-8, 8):
                p_y = int(y + row)
                p_x = int(x + col)
                if p_y < 0 or p_y > src.shape[0] - 1 or p_x < 0 or p_x > src.shape[1] - 1:
                    continue # 이미지를 벗어나는 부분에 대한 처리
                gaussian_weight = np.exp((-1 / 16) * (row ** 2 + col ** 2))
                orient_hist[int(angle[p_y, p_x] // 10)] += magnitude[p_y, p_x] * gaussian_weight
        ###################################################################
        ## ToDo
        ## orient_hist에서 가중치가 가장 큰 값을 추출하여 keypoint의 angle으로 설정
        ## 가장 큰 가중치의 0.8배보다 큰 가중치의 값도 keypoint의 angle로 설정
        ## keypoint 저장에는 KeyPoint module을 사용할 것 
        ## angle은 0 ~ 360도의 표현으로 저장해야 함
        ## np.max, np.argmax를 활용하면 쉽게 구할 수 있음
        ## keypoints[i].angle = ???
        ###################################################################
        max_orient_hist = np.max(orient_hist)
        argmax_orient_hist = np.argmax(orient_hist)
        keypoint_angle = int(argmax_orient_hist) * 10
        keypoints[i].angle = keypoint_angle

        for j in range(len(orient_hist)) :
            if j != int(argmax_orient_hist) and orient_hist[j] > (0.8 * max_orient_hist) :
                keypoints.append(KeyPoint(x, y, keypoints[i].size, j * 10, keypoints[i].response, keypoints[i].octave, keypoints[i].class_id))
        
        assert keypoints[i].angle != -1

        # 여기서 값이 큰 각도 자체는 이미 뽑음


    print('calculate descriptor')
    descriptors = np.zeros((len(keypoints), 128))  # 8 orientation * 4 * 4 = 128 dimensions

    # 한 키포인트에서 ...
    for i in range(len(keypoints)):
        x, y = keypoints[i].pt
        theta = np.deg2rad(keypoints[i].angle)
        # 키포인트 각도 조정을 위한 cos, sin값
        cos_angle = np.cos(theta)
        sin_angle = np.sin(theta)

        # 한 키포인트 안에 있는 총 16개의 셀에 대해서 ...
        for row in range(-8, 8):
            for col in range(-8, 8):
                # 회전을 고려한 point값을 얻어냄
                row_rot = np.round((cos_angle * col) + (sin_angle * row))
                col_rot = np.round((cos_angle * col) - (sin_angle * row))

                p_y = int(y + row_rot)
                p_x = int(x + col_rot)
                if p_y < 0 or p_y > (src.shape[0] - 1) \
                        or p_x < 0 or p_x > (src.shape[1] - 1):
                    continue
                ###################################################################
                ## ToDo
                ## descriptor을 완성
                ## 4×4의 window에서 8개의 orientation histogram으로 표현
                ## 최종적으로 128개 (8개의 orientation * 4 * 4)의 descriptor를 가짐
                ## gaussian_weight = np.exp((-1 / 16) * (row_rot ** 2 + col_rot ** 2))
                ###################################################################
                gaussian_weight = np.exp((-1 / 32) * (row_rot ** 2 + col_rot ** 2))
                weight = np.sqrt((p_x ** 2) + (p_y ** 2)) * gaussian_weight

                descriptor_angle = (np.rad2deg(np.arctan2(p_y, p_x)) + 360) % 360 - keypoints[i].angle
                descriptor_angle = abs(descriptor_angle)

                x_group = (col + 8) // 4
                y_group = (row + 8) // 4
                index_start = (y_group * 32) + (x_group * 8)

                descriptors[i][index_start + int((descriptor_angle // 45))] += weight
                # print(f"{i} Descriptors : angle = {descriptor_angle}, angle_bin = {descriptor_angle // 45}, plus_weight = {weight}")
                keypoints[i].angle = descriptor_angle

        # print(descriptors[i])

    return keypoints, descriptors


def main():
    src = cv2.imread("CV/week6/homework/zebra.png")
    src_rotation = cv2.rotate(src, cv2.ROTATE_90_CLOCKWISE)

    kp1, des1 = SIFT(src)
    kp2, des2 = SIFT(src_rotation)

    ## Matching 부분 ## 
    bf = cv2.BFMatcher_create(cv2.NORM_HAMMING, crossCheck=True)
    des1 = des1.astype(np.uint8)
    des2 = des2.astype(np.uint8)
    matches = bf.match(des1, des2)
    matches = sorted(matches, key = lambda x:x.distance)

    result = cv2.drawMatches(src, kp1, src_rotation, kp2, matches[:20], outImg=None, flags=2)
    
    # 결과의 학번 작성하기!
    cv2.imshow('20211924_my_sift', result) 
    cv2.waitKey()
    cv2.destroyAllWindows()
    

if __name__ == '__main__':
    main()