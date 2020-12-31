import React, { Component } from "react";

class Input extends Component {
  state = {};
  render() {
    const {
      type,
      id,
      lable,
      lableSize,
      frmField,
      err,
      errMessage,
      inputRef,
      ...others
    } = this.props;
    const size = lableSize ? lableSize : 3;
    const classLeft = `col-sm-${size} col-form-label`;
    const classRight = `col-sm-${12 - size}`;
    const inputClass = `form-control ${err ? "is-invalid" : ""}`;
    return (
      <div className="form-group row">
        <label htmlFor={id} className={classLeft}>
          {lable}
        </label>
        <div className={classRight}>
          {others["row"] > 1 ? (
            <textarea
              ref={inputRef}
              className={inputClass}
              id={id}
              {...others}
              {...frmField}
            />
          ) : (
            <input
              ref={inputRef}
              className={inputClass}
              id={id}
              type={type}
              {...others}
              {...frmField}
            />
          )}
          {err ? <div className="invalid-feedback">{errMessage}</div> : null}
        </div>
      </div>
    );
  }
}

export default Input;
