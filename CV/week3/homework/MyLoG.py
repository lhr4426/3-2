import cv2
import numpy as np

def my_padding(src, filter):
    (h, w) = src.shape
    (h_pad, w_pad) = filter.shape
    h_pad = h_pad // 2
    w_pad = w_pad // 2
    padding_img = np.zeros((h+h_pad*2, w+w_pad*2))
    padding_img[h_pad:h+h_pad, w_pad:w+w_pad] = src
    return padding_img

# filter와 image를 입력받아 filtering수행
def my_filtering(src, filter):
    (h, w) = src.shape
    (m_h, m_w) = filter.shape
    pad_img = my_padding(src, filter)
    dst = np.zeros((h, w))
    for row in range(h):
        for col in range(w):
            dst[row, col] = np.sum(pad_img[row:row + m_h, col:col + m_w] * filter)
    return dst

def get_LoG_filter(fsize, sigma=1):
    y, x = np.mgrid[-(fsize//2):(fsize//2)+1, -(fsize//2):(fsize//2)+1]

    LoG = ((((x ** 2) + (y ** 2))/(sigma ** 4)) - (2 / (sigma ** 2)))  * np.exp(-((x ** 2 + y ** 2)/(2 * sigma ** 2)))
    # 필터의 총 합을 0으로 만들기
    LoG = LoG - (LoG.sum() / fsize ** 2)

    return LoG

if __name__ == '__main__':
    src = cv2.imread("../imgs/Lena.png", cv2.IMREAD_GRAYSCALE)

    LoG_fitler = get_LoG_filter(fsize=256, sigma=30)

    print(LoG_fitler)
    # draw mask
    LoG = ((LoG_fitler - np.min(LoG_fitler)) / np.max(LoG_fitler - np.min(LoG_fitler)) * 255).astype(np.uint8)
    cv2.imshow("log", LoG)
    cv2.waitKey()
    cv2.destroyAllWindows()

    LoG_fitler = get_LoG_filter(fsize=7, sigma=1)

    dst = my_filtering(src, LoG_fitler)

    dst = np.abs(dst)   # Equivalent to np.sqrt(dst**2)
    dst = dst - dst.min()
    dst = dst / dst.max()

    cv2.imshow('dst', dst)
    cv2.waitKey()
    cv2.destroyAllWindows()


