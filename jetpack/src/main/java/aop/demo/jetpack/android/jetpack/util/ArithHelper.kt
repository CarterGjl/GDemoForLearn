package aop.demo.jetpack.android.jetpack.util

import java.math.BigDecimal


object ArithHelper {

    // 默认除法运算精度
    private val DEF_DIV_SCALE = 16

    /**
     * 提供精确的加法运算。
     *
     * @param v1 被加数
     * @param v2 加数
     * @return 两个参数的和
     */

    fun add(v1: Double, v2: Double): Double {
        val b1 = BigDecimal(java.lang.Double.toString(v1))
        val b2 = BigDecimal(java.lang.Double.toString(v2))
        return b1.add(b2).toDouble()
    }

    fun add(v1: String, v2: String): Double {
        val b1 = BigDecimal(v1)
        val b2 = BigDecimal(v2)
        return b1.add(b2).toDouble()
    }

    /**
     * 提供精确的减法运算。
     *
     * @param v1 被减数
     * @param v2 减数
     * @return 两个参数的差
     */

    fun sub(v1: Double, v2: Double): Double {
        val b1 = BigDecimal(java.lang.Double.toString(v1))
        val b2 = BigDecimal(java.lang.Double.toString(v2))
        return b1.subtract(b2).toDouble()
    }

    fun sub(v1: String, v2: String): Double {
        val b1 = BigDecimal(v1)
        val b2 = BigDecimal(v2)
        return b1.subtract(b2).toDouble()
    }

    /**
     * 提供精确的乘法运算。
     *
     * @param v1 被乘数
     * @param v2 乘数
     * @return 两个参数的积
     */

    fun mul(v1: Double, v2: Double): Double {
        val b1 = BigDecimal(v1.toString())
        val b2 = BigDecimal(v2.toString())
        return b1.multiply(b2).toDouble()
    }

    fun mul(v1: Float, v2: Float): Float {
        val b1 = BigDecimal(java.lang.Double.toString(v1.toDouble()))
        val b2 = BigDecimal(java.lang.Double.toString(v2.toDouble()))
        return b1.multiply(b2).toFloat()
    }

    fun mul(v1: String, v2: String): Double {
        val b1 = BigDecimal(v1)
        val b2 = BigDecimal(v2)
        return b1.multiply(b2).toDouble()
    }

    fun div(v1: Float, v2: Float): Float {
        val b1 = BigDecimal(v1.toString())
        val b2 = BigDecimal(v2.toString())
        return b1.divide(b2, DEF_DIV_SCALE, BigDecimal.ROUND_HALF_UP).toFloat()
    }

    fun div(v1: String, v2: String): Double {
        val b1 = BigDecimal(v1)
        val b2 = BigDecimal(v2)
        return b1.divide(b2, DEF_DIV_SCALE, BigDecimal.ROUND_HALF_UP).toDouble()
    }

    fun div(v1: Int, v2: Int): Float {
        val b1 = BigDecimal(v1)
        val b2 = BigDecimal(v2)
        return b1.divide(b2, DEF_DIV_SCALE, BigDecimal.ROUND_HALF_UP).toFloat()
    }

    fun div(v1: Long, v2: Long): Float {
        val b1 = BigDecimal(v1)
        val b2 = BigDecimal(v2)
        return b1.divide(b2, DEF_DIV_SCALE, BigDecimal.ROUND_HALF_UP).toFloat()
    }

    /**
     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指 定精度，以后的数字四舍五入。
     *
     * @param v1    被除数
     * @param v2    除数
     * @param scale 表示表示需要精确到小数点以后几位。
     * @return 两个参数的商
     */

    @JvmOverloads
    fun div(v1: Double, v2: Double, scale: Int = DEF_DIV_SCALE): Double {
        require(scale >= 0) { "The   scale   must   be   a   positive   integer   or   zero" }
        val b1 = BigDecimal(java.lang.Double.toString(v1))
        val b2 = BigDecimal(java.lang.Double.toString(v2))
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).toDouble()
    }

    /**
     * 十进制转十六进制
     *
     * @param value
     * @return
     */
    fun intToHex(value: Int): String {
        var value = value
        var s = StringBuffer()
        val a: String
        val b = charArrayOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F')
        while (value != 0) {
            s = s.append(b[value % 16])
            value = value / 16
        }
        a = s.reverse().toString()
        return a
    }

    /**
     * 提供精确的小数位四舍五入处理。
     *
     * @param v     需要四舍五入的数字
     * @param scale 小数点后保留几位
     * @return 四舍五入后的结果
     */

    fun round(v: Double, scale: Int): Double {
        require(scale >= 0) { "The   scale   must   be   a   positive   integer   or   zero" }
        val b = BigDecimal(java.lang.Double.toString(v))
        val one = BigDecimal("1")
        return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).toDouble()
    }

    fun round(v: String, scale: Int): Double {
        require(scale >= 0) { "The   scale   must   be   a   positive   integer   or   zero" }
        val b = BigDecimal(v)
        val one = BigDecimal("1")
        return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).toDouble()
    }

    /**
     * 格式化视频时长字符串
     */
    fun formatDecimal(value: String): String {
        return if (value.endsWith(".0") || value.endsWith(".00")) {
            value.substring(0, value.indexOf("."))
        } else value
    }

    /**
     * 字符串是否为数字
     */
    fun isNumeric(str: String?): Boolean {
        return if (str != null && "" != str.trim { it <= ' ' }) {
            str.matches("^[0-9]*$".toRegex())
        } else false
    }

    /**
     * 数值升值格式化。逢单位"万"格式化
     *
     * @param number   要格式化的数
     * @param typeEnum [NumericFormatTypeEnum]
     * @return 单位"万"格式化数据内容
     */
    fun appreciationFormat(number: Long, typeEnum: NumericFormatTypeEnum): String {
        var result = number.toString() + ""
        val miriade = 10000
        if (number >= miriade) {
            val type: String
            if (typeEnum == NumericFormatTypeEnum.CHINESE) {
                type = "万"
            } else {
                type = "w"
            }
            result = (number / miriade).toString() + type
        }
        return result
    }

    /**
     * 数值升值格式化。逢单位"千"或以上单位格式化
     *
     * @param number   要格式化的数
     * @param typeEnum [NumericFormatTypeEnum]
     * @return 逢"千" 和 "万"格式化内容
     */
    fun appreciationThousandFormat(number: Float, typeEnum: NumericFormatTypeEnum): String {
        val thousand = 1000
        var result = number.toString() + ""
        if (number >= thousand) {
            val type: String

            val miriade = 10000
            if (number >= miriade) {
                if (typeEnum == NumericFormatTypeEnum.CHINESE) {
                    type = "万"
                } else {
                    type = "w"
                }
                result = (number / miriade).toString() + type
            } else {
                if (typeEnum == NumericFormatTypeEnum.CHINESE) {
                    type = "千"
                } else {
                    type = "k"
                }

                result = (number / thousand).toString() + type
            }
        }
        return result
    }

    /**
     * 数值升值格式化。逢单位"千"或以上单位格式化
     *
     * @param number   要格式化的数
     * @param typeEnum [NumericFormatTypeEnum]
     * @return 逢"千" 和 "万"格式化内容
     */
    fun appreciationThousandFormat(number: Int, typeEnum: NumericFormatTypeEnum): String {
        val thousand = 1000
        var result = number.toString() + ""
        if (number >= thousand) {
            val type: String

            val miriade = 10000
            if (number >= miriade) {
                if (typeEnum == NumericFormatTypeEnum.CHINESE) {
                    type = "万"
                } else {
                    type = "w"
                }
                result = (number / miriade).toString() + type
            } else {
                if (typeEnum == NumericFormatTypeEnum.CHINESE) {
                    type = "千"
                } else {
                    type = "k"
                }

                result = (number / thousand).toString() + type
            }
        }
        return result
    }

    /**
     * 转换类型枚举
     */
    enum class NumericFormatTypeEnum {
        /**
         * 中文提示语
         */
        CHINESE,
        /**
         * 英文提示语
         */
        ENGLISH
    }
}// 这个类不能实例化
/**
 * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到 小数点以后10位，以后的数字四舍五入。
 *
 * @param v1 被除数
 * @param v2 除数
 * @return 两个参数的商
 */