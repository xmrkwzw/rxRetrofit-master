package com.xmjj.rxretrofit_master.adapter;

import android.graphics.Point;

import com.xmjj.jujianglibrary.util.logger.Logger;

import static java.lang.Math.PI;


/**
 * @author genius
 *         这里的坐标系是X轴向右，Y轴向上的坐标系
 */
public class MyDegreeAdapter {

	private static final int ANGLE_0 = 0;
	private static final int ANGLE_90 = 90;
	private static final int ANGLE_180 = 180;
	private static final int ANGLE_360 = 360;



	enum _Quadrant {
		eQ_NONE,                                    //  在坐标轴上
		eQ_ONE,                                        //  第一象限
		eQ_TWO,                                        //	第二象限
		eQ_THREE,                                    //	第三象限
		eQ_FOUR                                        //	第四象限
	}


	/**
	 * @param point
	 * @return 获得点所在角度（点与坐标轴原点连线与Y正半轴的顺时针夹角）单位为度数
	 */
	public static int GetRadianByPos(Point point) {
		double dAngle = GetRadianByPosEx(point);
		int result = (int) (dAngle * (360 / (2 * PI)));
		Logger.d("result = " + result);
		return result;
	}

	/**
	 * @param point
	 * @return 获得Point点所在象限
	 */
	public static _Quadrant GetQuadrant(Point point) {
		if (point.x == 0 || point.y == 0) {
			return _Quadrant.eQ_NONE;
		}

		if (point.x > 0) {
			if (point.y > 0) {
				return _Quadrant.eQ_ONE;
			} else {
				return _Quadrant.eQ_FOUR;
			}

		} else {
			if (point.y < 0) {
				return _Quadrant.eQ_THREE;
			} else {
				return _Quadrant.eQ_TWO;
			}
		}
	}

	/**
	 * @param point
	 * @return 获得点所在角度（点与坐标轴原点连线与Y正半轴的顺时针夹角）单位为弧度
	 */
	private static double GetRadianByPosEx(Point point) {

		if (point.x == 0 && point.y == 0) {
			return 0;
		}


		double Sin = point.x  / Math.sqrt(point.x * point.x + point.y * point.y);
		double dAngle = Math.asin(Sin);

		Logger.d("dAngle = " +Sin+"---"+ dAngle);
		switch (GetQuadrant(point)) {
			case eQ_NONE:
				if (point.x == 0 && point.y == 0) {
					return 0;
				}

				if (point.x == 0) {
					if (point.y > 0) {
						return -PI / 2;
					} else {
						return PI / 2;
					}
				}

				if (point.y == 0) {
					if (point.x > 0) {
						return 0;
					} else {
						return PI / 2;
					}
				}

				break;
			case eQ_ONE:
				dAngle = dAngle - PI / 2;
				break;

			case eQ_TWO:
				dAngle = PI / 2 - dAngle;

				break;
			case eQ_THREE:
				dAngle = PI / 2 - dAngle;

				break;
			case eQ_FOUR:
				dAngle += 2 * PI;
				dAngle = dAngle - PI / 2;

				break;
		}

		return dAngle;

	}


	/*以逆时针转为例子 x正半轴对应的是0度角 逆时针旋转为负角度*/
	public static int getAngle(Point point) {

		double Sin = Math.abs(point.y / Math.sqrt(point.x * point.x + point.y * point.y));

		double dAngle = Math.asin(Sin);

		int degree = (int) Math.toDegrees(dAngle);

		Logger.d("degree = before" + degree);
		switch (GetQuadrant(point)) {
			case eQ_NONE:
				if (point.x == 0 && point.y == 0) {
					degree = 0 ;
				}

				if (point.x == 0) {
					if (point.y > 0) {
						degree = -ANGLE_90;
					} else {
						degree =  ANGLE_90;
					}
				}

				if (point.y == 0) {
					if (point.x > 0) {
						degree =  ANGLE_0;
					} else {
						degree = ANGLE_180;
					}
				}

				break;
			case eQ_ONE:
				degree = -degree;
				break;

			case eQ_TWO:
				degree = -(ANGLE_180-degree);

				break;
			case eQ_THREE:
				degree = -(degree+ANGLE_180);

				break;
			case eQ_FOUR:
				degree = -(ANGLE_360-degree);

				break;
		}

		return degree;
	}
}
