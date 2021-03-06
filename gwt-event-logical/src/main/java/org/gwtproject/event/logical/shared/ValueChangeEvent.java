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

/**
 * Represents a value change event.
 *
 * @param <T> the value about to be changed
 */
public class ValueChangeEvent<T> extends Event<ValueChangeHandler<T>> {

  /** Handler type. */
  private static Type<ValueChangeHandler<?>> TYPE;

  private final T value;

  /**
   * Creates a value change event.
   *
   * @param value the value
   */
  protected ValueChangeEvent(T value) {
    this.value = value;
  }

  /**
   * Fires a value change event on all registered handlers in the handler manager. If no such
   * handlers exist, this method will do nothing.
   *
   * @param <T> the old value type
   * @param source the source of the handlers
   * @param value the value
   */
  public static <T> void fire(HasValueChangeHandlers<T> source, T value) {
    if (TYPE != null) {
      ValueChangeEvent<T> event = new ValueChangeEvent<>(value);
      source.fireEvent(event);
    }
  }

  /**
   * Fires value change event if the old value is not equal to the new value. Use this call rather
   * than making the decision to short circuit yourself for safe handling of null.
   *
   * @param <T> the old value type
   * @param source the source of the handlers
   * @param oldValue the oldValue, may be null
   * @param newValue the newValue, may be null
   */
  public static <T> void fireIfNotEqual(HasValueChangeHandlers<T> source, T oldValue, T newValue) {
    if (shouldFire(source, oldValue, newValue)) {
      ValueChangeEvent<T> event = new ValueChangeEvent<>(newValue);
      source.fireEvent(event);
    }
  }

  /**
   * Convenience method to allow subtypes to know when they should fire a value change event in a
   * null-safe manner.
   *
   * @param <T> value type
   * @param source the source
   * @param oldValue the old value
   * @param newValue the new value
   * @return whether the event should be fired
   */
  protected static <T> boolean shouldFire(
      HasValueChangeHandlers<T> source, T oldValue, T newValue) {
    return TYPE != null && oldValue != newValue && (oldValue == null || !oldValue.equals(newValue));
  }

  /**
   * Gets the type associated with this event.
   *
   * @return returns the handler type
   */
  public static Type<ValueChangeHandler<?>> getType() {
    if (TYPE == null) {
      TYPE = new Type<>();
    }
    return TYPE;
  }

  // The instance knows its BeforeSelectionHandler is of type I, but the TYPE
  // field itself does not, so we have to do an unsafe cast here.
  @SuppressWarnings({"unchecked", "rawtypes"})
  @Override
  public final Type<ValueChangeHandler<T>> getAssociatedType() {
    return (Type) TYPE;
  }

  @Override
  public String toDebugString() {
    return super.toDebugString() + getValue();
  }

  /**
   * Gets the value.
   *
   * @return the value
   */
  public T getValue() {
    return value;
  }

  @Override
  protected void dispatch(ValueChangeHandler<T> handler) {
    handler.onValueChange(this);
  }
}
