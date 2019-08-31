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

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import static java.util.Objects.requireNonNull;

/**
 * @author Mark Jeronimus
 */
// Created 2016-04-17
public class URLBuilder {
	private final String             host;
	private final Collection<String> parameters = new ArrayList<>(4);

	public URLBuilder(String host) {
		this.host = requireNonNull(host);
	}

	public URLBuilder addParameter(String parameter) {
		if (parameter.indexOf('?') != -1)
			throw new IllegalArgumentException("parameter may not contain unescaped '?': " + parameter);
		if (parameter.indexOf('&') != -1)
			throw new IllegalArgumentException("parameter may not contain unescaped '&': " + parameter);

		parameters.add(parameter);
		return this;
	}

	public URL toURL() {
		StringBuilder url = new StringBuilder(host);

		char separatorChar = '?';
		for (String parameter : parameters) {
			url.append(separatorChar).append(parameter);
			separatorChar = '&';
		}

		try {
			return new URL(url.toString());
		} catch (MalformedURLException e) {
			throw new IllegalStateException(e);
		}
	}
}
