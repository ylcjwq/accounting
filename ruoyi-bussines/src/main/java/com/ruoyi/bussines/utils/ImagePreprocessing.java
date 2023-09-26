package com.ruoyi.bussines.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImagePreprocessing {

    public static void main(String[] args) {
        String imagePath = "C:\\Users\\admin\\Desktop\\5.jpg";  // 图像文件路径

        try {
            BufferedImage originalImage = ImageIO.read(new File(imagePath));

            // 灰度化
            BufferedImage grayscaleImage = convertToGrayscale(originalImage);
            saveImage(grayscaleImage, "grayscale.jpg");

            // 二值化
            BufferedImage binaryImage = binarizeImage(grayscaleImage);
            saveImage(binaryImage, "binary.jpg");

            // 去噪
            BufferedImage denoisedImage = denoiseImage(binaryImage);
            saveImage(denoisedImage, "denoised.jpg");

            System.out.println("Preprocessing completed.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 灰度化
    private static BufferedImage convertToGrayscale(BufferedImage originalImage) {
        BufferedImage grayscaleImage = new BufferedImage(
                originalImage.getWidth(),
                originalImage.getHeight(),
                BufferedImage.TYPE_BYTE_GRAY);

        // 将原图转换为灰度图
        for (int i = 0; i < originalImage.getWidth(); i++) {
            for (int j = 0; j < originalImage.getHeight(); j++) {
                int rgb = originalImage.getRGB(i, j);
                grayscaleImage.setRGB(i, j, rgb);
            }
        }

        return grayscaleImage;
    }

    // 二值化
    private static BufferedImage binarizeImage(BufferedImage originalImage) {
        int width = originalImage.getWidth();
        int height = originalImage.getHeight();

        BufferedImage binarizedImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_BINARY);

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int rgb = originalImage.getRGB(i, j);
                Color color = new Color(rgb);
                int grayscaleValue = (int) (color.getRed() * 0.3 + color.getGreen() * 0.59 + color.getBlue() * 0.11);

                // 如果灰度值大于阈值，则设置为白色，否则设置为黑色
                int binarizedColor = (grayscaleValue > 128) ? Color.WHITE.getRGB() : Color.BLACK.getRGB();
                binarizedImage.setRGB(i, j, binarizedColor);
            }
        }

        return binarizedImage;
    }

    // 去噪
    private static BufferedImage denoiseImage(BufferedImage originalImage) {
        int width = originalImage.getWidth();
        int height = originalImage.getHeight();

        BufferedImage denoisedImage = new BufferedImage(
                width,
                height,
                BufferedImage.TYPE_INT_RGB);

        int filterSize = 3;  // 滤波器大小，这里使用3x3的简单平均滤波器

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int[] pixels = new int[filterSize * filterSize];
                int count = 0;

                // 收集滤波器范围内的像素值
                for (int m = i - 1; m <= i + 1; m++) {
                    for (int n = j - 1; n <= j + 1; n++) {
                        if (m >= 0 && m < width && n >= 0 && n < height) {
                            pixels[count] = originalImage.getRGB(m, n);
                            count++;
                        }
                    }
                }

                // 计算平均值
                int sumR = 0, sumG = 0, sumB = 0;
                for (int k = 0; k < count; k++) {
                    sumR += (pixels[k] >> 16) & 0xFF;
                    sumG += (pixels[k] >> 8) & 0xFF;
                    sumB += pixels[k] & 0xFF;
                }

                int avgR = sumR / count;
                int avgG = sumG / count;
                int avgB = sumB / count;

                // 设置去噪后的像素值
                int denoisedPixel = (avgR << 16) | (avgG << 8) | avgB;
                denoisedImage.setRGB(i, j, denoisedPixel);
            }
        }
        return denoisedImage;
    }

    // 保存图像到文件
    private static void saveImage(BufferedImage image, String outputPath) {
        try {
            ImageIO.write(image, "jpg", new File(outputPath));
            System.out.println("Image saved to: " + outputPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
