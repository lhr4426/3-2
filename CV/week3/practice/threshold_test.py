import cv2
import numpy as np

if __name__ == '__main__' :
    path = 'D:\\Rocky\\Gits\\3-2\\CV\\week3\\practice\\imgs\\threshold_test.png'
    src = np.fromfile(path, np.uint8)

    src = cv2.imdecode(src, cv2.IMREAD_GRAYSCALE)
    # assert src != None

    # 임계값 수동설정
    # ret, dst = cv2.threshold(src, 150, 255, cv2.THRESH_BINARY) 

    # 임계값 자동설정
    ret, dst = cv2.threshold(src, 0, 255, cv2.THRESH_OTSU)

    print('ret : ', ret)

    cv2.imshow('original', src)
    cv2.imshow('threshold', dst)

    cv2.waitKey()
    cv2.destroyAllWindows()