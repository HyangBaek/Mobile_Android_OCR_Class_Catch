//package com.example.ocrclasscatchproject.util
//
//import android.graphics.*
//
///**
// * 이미지 전처리 관련 함수 모음
// * OCR 인식률 향상을 위해 그레이스케일 변환, 대비 향상, 샤프닝, 확대, 이진화, 색상 반전 등을 수행
// */
//object ImagePreprocessor {
//
//    /**
//     * 입력 이미지를 그레이스케일로 변환
//     */
//    fun toGrayscale(bitmap: Bitmap): Bitmap {
//        val gray = Bitmap.createBitmap(bitmap.width, bitmap.height, Bitmap.Config.ARGB_8888)
//        val canvas = Canvas(gray)
//        val paint = Paint()
//        val matrix = ColorMatrix().apply { setSaturation(0f) } // 채도를 0으로 하여 흑백 처리
//        paint.colorFilter = ColorMatrixColorFilter(matrix)
//        canvas.drawBitmap(bitmap, 0f, 0f, paint)
//        return gray
//    }
//
//    /**
//     * 대비(Contrast)를 강화
//     * contrast 값으로 픽셀 밝기를 조정하여 선명도를 높임
//     */
//    fun enhanceContrast(bitmap: Bitmap): Bitmap {
//        val contrasted = Bitmap.createBitmap(bitmap.width, bitmap.height, Bitmap.Config.ARGB_8888)
//        val canvas = Canvas(contrasted)
//        val paint = Paint()
//        val contrast = 1.5f
//        val translate = (-0.5f * contrast + 0.5f) * 255f // 밝기 이동값 계산
//        val cm = ColorMatrix(floatArrayOf(
//            contrast, 0f, 0f, 0f, translate,
//            0f, contrast, 0f, 0f, translate,
//            0f, 0f, contrast, 0f, translate,
//            0f, 0f, 0f, 1f, 0f
//        ))
//        paint.colorFilter = ColorMatrixColorFilter(cm)
//        canvas.drawBitmap(bitmap, 0f, 0f, paint)
//        return contrasted
//    }
//
//    /**
//     * 샤프닝 필터 적용
//     * 주변 픽셀과의 차이를 강조하여 경계가 뚜렷해짐
//     */
//    fun sharpen(bitmap: Bitmap): Bitmap {
//        val width = bitmap.width
//        val height = bitmap.height
//        val config = bitmap.config ?: Bitmap.Config.ARGB_8888
//        val sharpened = Bitmap.createBitmap(width, height, config)
//
//        // 샤프닝 커널 행렬 (3x3)
//        val kernel = arrayOf(
//            floatArrayOf(0f, -1f, 0f),
//            floatArrayOf(-1f, 5f, -1f),
//            floatArrayOf(0f, -1f, 0f)
//        )
//
//        val pixels = IntArray(width * height)
//        bitmap.getPixels(pixels, 0, width, 0, 0, width, height)
//
//        val newPixels = pixels.copyOf()
//
//        // 픽셀 단위로 샤프닝 커널 적용 (가장자리 제외)
//        for (y in 1 until height - 1) {
//            for (x in 1 until width - 1) {
//                var r = 0f
//                var g = 0f
//                var b = 0f
//                for (ky in -1..1) {
//                    for (kx in -1..1) {
//                        val pixel = pixels[(x + kx) + (y + ky) * width]
//                        val weight = kernel[ky + 1][kx + 1]
//                        r += ((pixel shr 16) and 0xFF) * weight
//                        g += ((pixel shr 8) and 0xFF) * weight
//                        b += (pixel and 0xFF) * weight
//                    }
//                }
//                val newR = r.coerceIn(0f, 255f).toInt()
//                val newG = g.coerceIn(0f, 255f).toInt()
//                val newB = b.coerceIn(0f, 255f).toInt()
//                newPixels[y * width + x] = Color.rgb(newR, newG, newB)
//            }
//        }
//
//        sharpened.setPixels(newPixels, 0, width, 0, 0, width, height)
//        return sharpened
//    }
//
//    /**
//     * 이미지 크기 확대
//     * 기본 목표 가로 폭은 1024px, 비율에 맞게 세로도 조절
//     * 이미 목표 크기 이상일 경우 원본 반환
//     */
//    fun upscale(bitmap: Bitmap, targetWidth: Int = 1024): Bitmap {
//        if (bitmap.width >= targetWidth) return bitmap
//        val aspectRatio = bitmap.height.toFloat() / bitmap.width
//        val targetHeight = (targetWidth * aspectRatio).toInt()
//        return Bitmap.createScaledBitmap(bitmap, targetWidth, targetHeight, true)
//    }
//
//    /**
//     * 이진화 (Thresholding)
//     * 픽셀 밝기가 기준(threshold) 이하이면 검은색, 이상이면 흰색으로 변경
//     */
//    fun threshold(bitmap: Bitmap, threshold: Int = 127): Bitmap {
//        val width = bitmap.width
//        val height = bitmap.height
//        val binarized = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
//
//        for (y in 0 until height) {
//            for (x in 0 until width) {
//                val pixel = bitmap.getPixel(x, y)
//                val gray = (Color.red(pixel) + Color.green(pixel) + Color.blue(pixel)) / 3
//                val newColor = if (gray < threshold) Color.BLACK else Color.WHITE
//                val newPixel = Color.rgb(newColor, newColor, newColor)
//                binarized.setPixel(x, y, newPixel)
//            }
//        }
//        return binarized
//    }
//
//    /**
//     * 이미지 색상 반전 (Invert)
//     * 모든 픽셀 색상 값을 반전시킴 (255 - 원래값)
//     */
//    fun invert(bitmap: Bitmap): Bitmap {
//        val inverted = Bitmap.createBitmap(bitmap.width, bitmap.height, bitmap.config ?: Bitmap.Config.ARGB_8888)
//        val canvas = Canvas(inverted)
//        val paint = Paint()
//
//        val colorMatrixInversion = ColorMatrix(
//            floatArrayOf(
//                -1f,  0f,  0f, 0f, 255f,
//                0f, -1f,  0f, 0f, 255f,
//                0f,  0f, -1f, 0f, 255f,
//                0f,  0f,  0f, 1f,   0f
//            )
//        )
//
//        paint.colorFilter = ColorMatrixColorFilter(colorMatrixInversion)
//        canvas.drawBitmap(bitmap, 0f, 0f, paint)
//
//        return inverted
//    }
//}
