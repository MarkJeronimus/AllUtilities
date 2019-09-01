/*
 * This file is part of AllUtilities.
 *
 * Copyleft 2019 Mark Jeronimus. All Rights Reversed.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with AllUtilities. If not, see <http://www.gnu.org/licenses/>.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.digitalmodular.utilities.gui.transform;

/**
 * @author Mark Jeronimus
 */
// Created 2009-04-21
public interface TransformFloat3D {
	void setIdentity();

	float transformX(float x, float y, float z);

	float transformY(float x, float y, float z);

	float transformZ(float x, float y, float z);

	float transformRelativeX(float x, float y, float z);

	float transformRelativeY(float x, float y, float z);

	float transformRelativeZ(float x, float y, float z);

	float transformR(float r);

	float reverseX(float x, float y, float z);

	float reverseY(float x, float y, float z);

	float reverseZ(float x, float y, float z);

	float reverseRelativeX(float x, float y, float z);

	float reverseRelativeY(float x, float y, float z);

	float reverseRelativeZ(float x, float y, float z);

	float reverseR(float r);
}
