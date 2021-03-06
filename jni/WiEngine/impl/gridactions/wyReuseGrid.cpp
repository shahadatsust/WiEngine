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
#include "wyReuseGrid.h"
#include "wyNode.h"
#include "wyLog.h"

wyAction* wyReuseGrid::copy() {
	return wyReuseGrid::make(times);
}

wyAction* wyReuseGrid::reverse() {
	return wyReuseGrid::make(-times);
}

void wyReuseGrid::start(wyNode* target) {
	wyInstantAction::start(target);
    if(target->getGrid() != NULL && target->getGrid()->isActive())
    	target->getGrid()->addReuseCount(times);
}

wyReuseGrid::~wyReuseGrid() {
}

wyReuseGrid::wyReuseGrid(int times) : times(times) {
}

wyReuseGrid* wyReuseGrid::make(int times) {
	wyReuseGrid* a = WYNEW wyReuseGrid(times);
	return (wyReuseGrid*)a->autoRelease();
}
