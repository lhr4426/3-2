import numpy as np
import cv2

def my_padding(src, kernel):
    (h, w) = src.shape
    (h_pad, w_pad) = kernel.shape
    h_pad = h_pad // 2
    w_pad = w_pad // 2
    padding_img = np.zeros((h+h_pad*2, w+w_pad*2))
    padding_img[h_pad:h+h_pad, w_pad:w+w_pad] = src
    return padding_img

def my_filtering(src, kernel):
    (h, w) = src.shape
    (k_h, k_w) = kernel.shape
    pad_img = my_padding(src, kernel)
    dst = np.zeros((h, w)) #output

    for m in range(h):
        for n in range(w):
            sum = 0
            for k in range(k_h):
                for l in range(k_w):
                    sum += kernel[k, l] * pad_img[m + k, n + l]
            dst[m, n] = sum

    dst = (dst + 0.5).astype(np.uint8)
    return dst

def get_my_sobel() :
    sobel_x = np.dot(np.array([[1],[2],[1]]), np.array([[-1, 0, 1]]))
    sobel_y = np.dot(np.array([[-1], [0], [1]]), np.array([[1, 2, 1]]))
    return sobel_x, sobel_y

if __name__ == '__main__' :
    src = cv2.imread('CV\week3\practice\imgs\edge_detection_img.png', cv2.IMREAD_GRAYSCALE)
    sobel_x, sobel_y = get_my_sobel()
    dst_x = my_filtering(src, sobel_x)
    dst_y = my_filtering(src, sobel_y)
    dst = np.hypot(dst_x, dst_y)

    dst_x_Norm = ((dst_x - np.min(dst_x)) / np.max(dst_x - np.min(dst_x)) * 255 + 0.5).astype(np.uint8)
    dst_y_Norm = ((dst_y - np.min(dst_y)) / np.max(dst_y - np.min(dst_y)) * 255 + 0.5).astype(np.uint8)

    cv2.imshow('original', src)
    cv2.imshow('dst_x_Norm', dst_x_Norm)
    cv2.imshow('dst_y_Norm', dst_y_Norm)
    cv2.imshow('sobel', (dst+0.5).astype(np.uint8))

    cv2.waitKey()
    cv2.destroyAllWindows()
