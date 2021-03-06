/*
 * Copyright (c) 2010 WiYun Inc.
 * Author: luma(stubma@gmail.com)
 *
 * For all entities this program is free software; you can redistribute
 * it and/or modify it under the terms of the 'WiEngine' license with
 * the additional provision that 'WiEngine' must be credited in a manner
 * that can be be observed by end users, for example, in the credits or during
 * start up. (please find WiEngine logo in sdk's logo folder)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.wiyun.engine.types;

import static com.wiyun.engine.types.WYPoint.*;

public class WYLagrangeConfig {
	/**
	 * true表示这是一个三次拉格朗日曲线，false表示这是一个二次拉格朗日曲线
	 */
	boolean cubic;
	
    /**
     * startPosition of the bezier
     */
	public float startX, startY;
	
	/**
	 * end position of the bezier
	 */
	public float endX, endY;
	
	/**
	 * Bezier control point 1
	 */
	public float cp1X, cp1Y;
	
	/**
	 * Bezier control point 2
	 */
	public float cp2X, cp2Y;
	
	/**
	 * knot
	 */
	public float t0, t1, t2, t3;
	
	/**
	 * 创建一个二次拉格朗日曲线配置
	 *
	 * @param startX 起始点x位置
	 * @param startY 起始点y位置
	 * @param endX 结束点x位置
	 * @param endY 结束点y位置
	 * @param cp1X 控制点x位置
	 * @param cp1Y 控制点y位置
	 * @return \link WYLagrangeConfig WYLagrangeConfig\endlink
	 */
	public static WYLagrangeConfig makeQuad(float startX, float startY, float endX, float endY, float cp1X, float cp1Y) {
		WYLagrangeConfig c = new WYLagrangeConfig();
		c.startX = startX;
		c.startY = startY;
		c.endX = endX;
		c.endY = endY;
		c.cp1X = cp1X;
		c.cp1Y = cp1Y;
		c.cubic = false;
		c.t0 = 0;
		c.t1 = 0.5f;
		c.t2 = 1.f;
		c.t3 = 0;
		return c;
	}
	
	/**
	 * 创建一个三次拉格朗日曲线配置
	 *
	 * @param startX 起始点x位置
	 * @param startY 起始点y位置
	 * @param endX 结束点x位置
	 * @param endY 结束点y位置
	 * @param cp1X 第一控制点x位置
	 * @param cp1Y 第一控制点y位置
	 * @param cp2X 第二控制点x位置
	 * @param cp2Y 第二控制点y位置
	 * @return \link WYLagrangeConfig WYLagrangeConfig\endlink
	 */
	public static WYLagrangeConfig makeCubic(float startX, float startY, float endX, float endY, float cp1X, float cp1Y, float cp2X, float cp2Y) {
		WYLagrangeConfig c = new WYLagrangeConfig();
		c.startX = startX;
		c.startY = startY;
		c.endX = endX;
		c.endY = endY;
		c.cp1X = cp1X;
		c.cp1Y = cp1Y;
		c.cp2X = cp2X;
		c.cp2Y = cp2Y;
		c.cubic = true;
		c.t0 = 0;
		c.t1 = 0.33333f;
		c.t2 = 0.66666f;
		c.t3 = 1;
		return c;
	}
	
	/**
	 * 给定一个t值，区间0到1，计算拉格朗日曲线在t位置的坐标
	 *
	 * @param t 取样值，从0到1
	 * @return 计算出来的点坐标
	 */
	public WYPoint pointAt(float t) {
		if(cubic) {
			float[] ti = { t0, t1, t2, t3 };
			float[] bF = { 0, 0, 0, 0 };
			for(int j = 0; j < 4; j++) {
				float P = 1;
				for(int i = 0; i < 4; i++) {
					if(i != j)
						P = P * (t - ti[i]) / (ti[j] - ti[i]);
				}
				bF[j] = P;
			}

			WYPoint start = make(startX, startY);
			start = mul(start, bF[0]);
			WYPoint cp1 = make(cp1X, cp1Y);
			cp1 = mul(cp1, bF[1]);
			WYPoint cp2 = make(cp2X, cp2Y);
			cp2 = mul(cp2, bF[2]);
			WYPoint end = make(endX, endY);
			end = mul(end, bF[3]);
			return add(add(add(start, cp1), cp2), end);
		} else {
			float ti[] = { t0, t1, t2 };
			float[] bF = { 0, 0, 0 };
			for(int j = 0; j < 3; j++) {
				float P = 1;
				for(int i = 0; i < 3; i++) {
					if(i != j)
						P = P * (t - ti[i]) / (ti[j] - ti[i]);
				}
				bF[j] = P;
			}

			WYPoint start = make(startX, startY);
			start = mul(start, bF[0]);
			WYPoint cp1 = make(cp1X, cp1Y);
			cp1 = mul(cp1, bF[1]);
			WYPoint end = make(endX, endY);
			end = mul(end, bF[2]);
			return add(add(start, cp1), end);
		}
	}
}
