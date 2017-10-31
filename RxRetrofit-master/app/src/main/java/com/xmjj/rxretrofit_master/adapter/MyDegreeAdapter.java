package com.xmjj.rxretrofit_master.adapter;

import android.graphics.Point;


/**
 * @author genius
 * 这里的坐标系是X轴向右，Y轴向上的坐标系
 */
public class MyDegreeAdapter {

	private final static double PI = 3.1415926;
	
	enum _Quadrant{
		eQ_NONE,									//  在坐标轴上
		eQ_ONE,										//  第一象限
		eQ_TWO,										//	第二象限
		eQ_THREE,									//	第三象限
		eQ_FOUR										//	第四象限
	}
	
	
	
	/**
	 * @param point
	 * @return
	 * 
	 * 获得Point点所在象限
	 */
	public static _Quadrant GetQuadrant(Point point){
			if (point.x == 0 || point.y == 0)
			{
				return _Quadrant.eQ_NONE;
			}
			
			if (point.x > 0)
			{
				if (point.y > 0)
				{
					return _Quadrant.eQ_ONE;
				}
				else
				{
					return _Quadrant.eQ_TWO;
				}

			}
			else
			{
				if (point.y < 0)
				{
					return _Quadrant.eQ_THREE;
				}
				else
				{
					return _Quadrant.eQ_FOUR;
				}
			}
	}
	
	/**
	 * @param point
	 * @return
	 * 
	 * 获得点所在角度（点与坐标轴原点连线与Y正半轴的顺时针夹角）单位为度数
	 */
	public static int GetRadianByPos(Point point){
		double dAngle = GetRadianByPosEx(point);
		
		return (int) (dAngle * (360 / (2 * PI)));
	}
	
	/**
	 * @param point
	 * @return
	 * 
	 * 获得点所在角度（点与坐标轴原点连线与Y正半轴的顺时针夹角）单位为弧度
	 */
	private static double GetRadianByPosEx(Point point){
		
		if (point.x == 0 && point.y == 0)
		{
			return 0;
		}


		double Sin = point.x / Math.sqrt(point.x * point.x + point.y * point.y);
		double dAngle = Math.asin(Sin);

		switch(GetQuadrant(point))
		{
		case eQ_NONE:
			{
				if (point.x == 0 && point.y == 0)
				{
					return 0;
				}

				if (point.x == 0)
				{
					if (point.y > 0)
					{
						return 0;
					}
					else
					{
						return PI;
					}
				}
				
				if (point.y == 0)
				{
					if (point.x > 0)
					{
						return PI/2;
					}
					else
					{
						return (float) (1.5*PI);
					}
				}
			}
			break;
		case eQ_ONE:
			{
				return dAngle;
			}
		case eQ_TWO:
			{
				dAngle = PI - dAngle;
			}
			break;
		case eQ_THREE:
			{
				dAngle = PI - dAngle;
			}
			break;
		case eQ_FOUR:
			{
				dAngle += 2*PI;
			}
			break;
		}

		return dAngle;
		
	}
}
