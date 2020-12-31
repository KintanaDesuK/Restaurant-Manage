import React, { useState, Fragment, useEffect } from "react";
import { Button, Modal } from "react-bootstrap";
import Input from "../controll/input";
import { useFormik, yupToFormErrors } from "formik";
import * as Yup from "yup";
const Ingredient = (props) => {
  const formik = useFormik({
    initialValues: {
      ingName: "",
      ingPrice: "",
      quantity: "",
      unit: "",
      ingCategory: "",
    },
    validationSchema: Yup.object({
      ingName: Yup.string().required("Require"),
      ingPrice: Yup.string().required("Require"),
      quantity: Yup.string().required("Require"),
      unit: Yup.string().required("Require"),
      ingCategory: Yup.string("Loại nguyên liệu").required("Require"),
    }),
    onSubmit: (values) => {
      handleFormSubmit(values);
    },
  });

  const [modalShow, setModalShow] = useState(false);

  const handleModalClose = () => {
    setModalShow(false);
  };
  const handleModalShow = (e, dataId) => {
    if (e) e.preventDefault();
    // setMajorId(dataId);
    // if (dataId > 0) {
    //   majorService.get(dataId).then((res) => {
    //     formik.setValues(res.data);
    setModalShow(true);
    //   });
    // } else {
    //   formik.resetForm();
    //   setModalShow(true);
    // }
  };

  const handleFormSubmit = (data) => {};

  return (
    <Fragment>
      <div className="container mt-4">
        <div className="card border-primary bt-primary-5">
          <div className="card-header">
            <div className="row">
              <div className="col">
                <h3 className="card-title">Nguyên liệu</h3>
              </div>
              <div>
                <input
                  placeholder="Tìm kiếm theo tên"
                  id="txtSearch"
                  type="text"
                  className="form-control"
                ></input>
              </div>
              <div className="col-auto">
                <button type="button" className="btn btn-primary">
                  <i class="fas fa-search"></i>
                </button>
              </div>
              <div className="col-auto">
                <button
                  type="button"
                  className="btn btn-primary"
                  data-toggle="modal"
                  onClick={() => handleModalShow(null, 0)}
                >
                  <i className="fas fa-plus" /> Thêm
                </button>
              </div>
            </div>
          </div>
          <div className="card-body">
            <div className="table-responsive">
              <table className="table table-bordered table-hover table-striped">
                <thead>
                  <tr className="table-primary">
                    <th style={{ width: "50px" }}>#</th>
                    <th>Tên nguyên liệu</th>
                    <th>Giá</th>
                    <th>Số lượng</th>
                    <th>Đơn vị tính</th>
                    <th>Loại nguyên liệu</th>
                    <th style={{ width: "80px" }} />
                  </tr>
                </thead>
                <tbody>
                  {/* {majors.map((major, idx) => {
                    return (
                      <tr key={major.id}>
                        <td>{idx + 1}</td>
                        <td>{major.name}</td>
                        <td>
                          <a
                            href="/#"
                            onClick={(e) => handleModalShow(e, major.id)}
                          >
                            <i className="fas fa-edit text-primary" />
                          </a>
                          <a href="/#" onClick={(e) => deleteRow(e, major.id)}>
                            <i className="fas fa-trash-alt text-danger" />
                          </a>
                        </td>
                      </tr>
                    );
                  })} */}
                  <tr>
                    <td>2</td>
                    <td>Marketing</td>
                    <td>Marketing</td>
                    <td>Marketing</td>
                    <td>Marketing</td>
                    <td>Marketing</td>

                    <td>
                      <a href="/#" onClick={(e) => handleModalShow(e)}>
                        <i className="fas fa-edit text-primary" />
                      </a>
                      <a href="/#">
                        <i className="fas fa-trash-alt text-danger ml-2" />
                      </a>
                    </td>
                  </tr>
                </tbody>
              </table>
            </div>
          </div>
        </div>
      </div>

      <Modal
        show={modalShow}
        onHide={handleModalClose}
        backdrop="static"
        keyboard={false}
      >
        <Modal.Header closeButton>
          <Modal.Title>Món ăn</Modal.Title>
        </Modal.Header>
        <form onSubmit={formik.handleSubmit}>
          <Modal.Body>
            <Input
              id="txtIngName"
              type="text"
              lable="Tên nguyên liệu"
              maxLength="100"
              frmField={formik.getFieldProps("ingName")}
              err={formik.touched.ingName && formik.errors.ingName}
              errMessage={formik.errors.ingName}
            ></Input>
            <Input
              id="txtIngPrice"
              type="number"
              lable="Giá"
              maxLength="100"
              frmField={formik.getFieldProps("ingPrice")}
              err={formik.touched.ingPrice && formik.errors.ingPrice}
              errMessage={formik.errors.ingPrice}
            ></Input>
            <Input
              id="txtQuantity"
              type="number"
              lable="Số lượng"
              maxLength="100"
              frmField={formik.getFieldProps("quantity")}
              err={formik.touched.quantity && formik.errors.quantity}
              errMessage={formik.errors.quantity}
            ></Input>
            <Input
              id="txtUnit"
              type="text"
              lable="Đơn vị tính"
              maxLength="100"
              frmField={formik.getFieldProps("unit")}
              err={formik.touched.unit && formik.errors.unit}
              errMessage={formik.errors.unit}
            ></Input>
            <select
              className="form-control mb-4"
              id="ingCategory"
              frmField={formik.getFieldProps("ingCategory")}
              err={formik.touched.ingCategory && formik.errors.ingCategory}
              errMessage={formik.errors.ingCategory}
            >
              <option value="ingCategory">Loại nguyên liệu</option>
            </select>
          </Modal.Body>
          <Modal.Footer>
            <Button variant="secondary" onClick={handleModalClose}>
              Close
            </Button>
            <Button
              variant="primary"
              type="submit"
              // disabled={!(formik.dirty && formik.isValid)}
            >
              Save
            </Button>
          </Modal.Footer>
        </form>
      </Modal>
    </Fragment>
  );
};

export default Ingredient;
