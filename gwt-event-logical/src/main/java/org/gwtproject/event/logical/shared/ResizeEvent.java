/*
 * Copyright 2008 The GWT Project Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package org.gwtproject.event.logical.shared;

import org.gwtproject.event.shared.Event;
import org.gwtproject.event.shared.HasHandlers;

/** Fired when the event source is resized. */
public class ResizeEvent extends Event<ResizeHandler> {

  /** The event type. */
  private static Type<ResizeHandler> TYPE;

  private final int width;
  private final int height;

  /**
   * Construct a new {@link ResizeEvent}.
   *
   * @param width the new width
   * @param height the new height
   */
  protected ResizeEvent(int width, int height) {
    this.width = width;
    this.height = height;
  }

  /**
   * Fires a resize event on all registered handlers in the handler source.
   *
   * @param <S> The handler source
   * @param source the source of the handlers
   * @param width the new width
   * @param height the new height
   */
  public static <S extends HasResizeHandlers & HasHandlers> void fire(
      S source, int width, int height) {
    if (TYPE != null) {
      ResizeEvent event = new ResizeEvent(width, height);
      source.fireEvent(event);
    }
  }

  /**
   * Ensures the existence of the handler hook and then returns it.
   *
   * @return returns a handler hook
   */
  public static Type<ResizeHandler> getType() {
    if (TYPE == null) {
      TYPE = new Type<>();
    }
    return TYPE;
  }

  @Override
  public final Type<ResizeHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  public String toDebugString() {
    return super.toDebugString() + " width = " + width + " height =" + height;
  }

  @Override
  protected void dispatch(ResizeHandler handler) {
    handler.onResize(this);
  }

  /**
   * Returns the new height.
   *
   * @return the new height
   */
  public int getHeight() {
    return height;
  }

  /**
   * Returns the new width.
   *
   * @return the new width
   */
  public int getWidth() {
    return width;
  }
}
