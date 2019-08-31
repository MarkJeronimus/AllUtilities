/*
 * This file is part of AllUtilities.
 *
 * Copyleft 2018 Mark Jeronimus. All Rights Reversed.
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
package org.digitalmodular.utilities.net;

import java.util.ArrayList;

/**
 * @author Mark Jeronimus
 */
// Created 2005-11-03
public class NetRequest {
	public static NetRequest InvalidArgumentCountError = new NetRequest("InvalidArgumentCountError()");
	public static NetRequest InvalidRequestError       = new NetRequest("InvalidRequestError()");
	public static NetRequest DisconnectRequest         = new NetRequest("Disconnect()");

	public ArrayList<Object> request;

	public NetRequest(ArrayList<Object> request) {
		this.request = request;
	}

	public NetRequest(String request) {
		this.request = new ArrayList<>();
		int x;

		x = request.indexOf('(');
		if (x < 1) {
			this.request = null;
			return;
		}
		this.request.add(request.substring(0, x));

		String  parameter = "";
		boolean literal   = false;
		try {
			x++;
			for (; x < request.length(); x++) {
				char c = request.charAt(x);
				if (literal) {
					parameter += c;
					if (c == '"') {
						this.request.add(parameter.trim());
						parameter = "";
						literal = false;
					}
				} else {
					if (c == ',' || c == ')') {
						parameter = parameter.trim();
						if (parameter.length() > 0) {
							this.request.add(new Double(Double.parseDouble(parameter.trim())));
							parameter = "";
						}
						if (c == ')') {
							break;
						}
					} else {
						if (c == '"') {
							if (parameter.length() > 0) {
								this.request = null;
								return;
							}
							literal = true;
						}
						parameter += c;
					}
				}
			}
		} catch (NumberFormatException e) {
			this.request = null;
			return;
		}
		if (x < request.length() - 1) {
			this.request = null;
			return;
		}
		if (literal) {
			this.request = null;
			return;
		}
		if (parameter.length() > 0) {
			this.request = null;
			return;
		}
	}

	public String getFunction() {
		return (String)request.get(0);
	}

	public int getNumArguments() {
		return request.size() - 1;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		StringBuffer out = new StringBuffer();
		out.append(this.getClass().getSimpleName() + "[request=" + request.get(0));

		out.append("(");
		if (request.size() > 1) {
			for (int i = 1; i < request.size(); i++) {
				if (i > 1) {
					out.append(", ");
				}
				Object value = request.get(i);
				if (value instanceof String || value instanceof char[]) {
					out.append(value);
				} else if (value instanceof Byte) {
					out.append("" + ((Byte)value).byteValue());
				} else if (value instanceof Character) {
					out.append(((Character)value).charValue());
				} else if (value instanceof Short) {
					out.append("" + ((Short)value).shortValue());
				} else if (value instanceof Integer) {
					out.append("" + ((Integer)value).intValue());
				} else if (value instanceof Long) {
					out.append("" + ((Long)value).longValue());
				} else if (value instanceof Float) {
					out.append("" + ((Integer)value).floatValue());
				} else if (value instanceof Double) {
					out.append("" + ((Integer)value).doubleValue());
				} else {
					out.append(value.toString());
				}
			}
		}
		out.append(")]");

		return out.toString();
	}
}
