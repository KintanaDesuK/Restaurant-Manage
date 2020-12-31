import React, { useState, Fragment, useEffect } from "react";
import { Button, Modal } from "react-bootstrap";
import Input from "../controll/input";
import { useFormik, yupToFormErrors } from "formik";
import * as Yup from "yup";
const Order = (props) => {
  const formik = useFormik({
    initialValues: {
      createDate: "",
      onsale: "",
      totalPrice: "",
      customer: "",
      staff: "",
      table: "",
    },
    validationSchema: Yup.object({}),
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

  const [orderDetailShow, setOrderDetailShow] = useState(false);

  const handleOrderDetailClose = () => {
    setOrderDetailShow(false);
  };
  const handleOrderDetailShow = (e, dataId) => {
    if (e) e.preventDefault();
    // setMajorId(dataId);
    // if (dataId > 0) {
    //   majorService.get(dataId).then((res) => {
    //     formik.setValues(res.data);
    setOrderDetailShow(true);
    //   });
    // } else {
    //   formik.resetForm();
    //   setModalShow(true);
    // }
  };

  const handleOrderDetailSubmit = (data) => {};

  return (
    <Fragment>
      <div className="container mt-4">
        <div className="card border-primary bt-primary-5">
          <div className="card-header">
            <div className="row">
              <div className="col">
                <h3 className="card-title">Hoá đơn</h3>
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
                {/* <button
                  type="button"
                  className="btn btn-primary"
                  data-toggle="modal"
                  onClick={() => handleModalShow(null, 0)}
                >
                  <i className="fas fa-plus" /> Thêm
                </button> */}
              </div>
            </div>
          </div>
          <div className="card-body">
            <div className="table-responsive">
              <table className="table table-bordered table-hover table-striped">
                <thead>
                  <tr className="table-primary">
                    <th style={{ width: "50px" }}>#</th>
                    <th>Ngày tạo</th>
                    <th>Khuyến mãi</th>
                    <th>Đã thanh toán</th>
                    <th>Tổng tiền</th>
                    <th>Khách hàng</th>
                    <th>Nhân viên</th>
                    <th>Bàn</th>
                    <th style={{ width: "90px" }} />
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
                    <td>Marketing</td>
                    <td>Marketing</td>

                    <td>
                      <a href="/#" onClick={(e) => handleModalShow(e)}>
                        <i className="fas fa-edit text-primary" />
                      </a>
                      <a href="/#">
                        <i className="fas fa-trash-alt text-danger ml-1" />
                      </a>
                      <a href="/#" onClick={(e) => handleOrderDetailShow(e)}>
                        <i class="fas fa-list-alt ml-1"></i>
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
        size="lg"
        centered
        show={orderDetailShow}
        onHide={handleOrderDetailClose}
        backdrop="static"
        keyboard={false}
      >
        <Modal.Header closeButton>
          <Modal.Title>Chi tiết hoá đơn</Modal.Title>
        </Modal.Header>
        <form onSubmit={formik.handleSubmit}>
          <Modal.Body>
            <div className="container mt-4 " width="1000px">
              <div className="card border-primary bt-primary-5">
                <div className="card-header">
                  <div className="row">
                    <div className="col">
                      <h3 className="card-title">Món ăn</h3>
                    </div>
                  </div>
                </div>
                <div className="card-body">
                  <div className="table-responsive">
                    <table className="table table-bordered table-hover table-striped">
                      <thead>
                        <tr className="table-primary">
                          <th style={{ width: "50px" }}>#</th>
                          <th>Món ăn</th>
                          <th>Số lượng</th>
                          <th>Giá</th>
                          <th>Hoá đơn</th>
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
                        </tr>
                      </tbody>
                    </table>
                  </div>
                </div>
              </div>
            </div>
          </Modal.Body>
        </form>
      </Modal>

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
              id="txtCreateDate"
              type="date"
              lable="Ngày tạo"
              maxLength="100"
              frmField={formik.getFieldProps("createDate")}
              err={formik.touched.createDate && formik.errors.createDate}
              errMessage={formik.errors.createDate}
            ></Input>
            <Input
              id="txtOnsale"
              type="number"
              lable="Khuyến mãi"
              maxLength="100"
              frmField={formik.getFieldProps("onsale")}
              err={formik.touched.onsale && formik.errors.onsale}
              errMessage={formik.errors.onsale}
            ></Input>
            <div className="mb-4">
              <div className="form-check">
                <input
                  className="form-check-input"
                  type="radio"
                  name="flexRadioDefault"
                  id="flexRadioDefault1"
                />
                <label className="form-check-label" htmlFor="flexRadioDefault1">
                  Đã thanh toán
                </label>
              </div>
              <div className="form-check">
                <input
                  className="form-check-input"
                  type="radio"
                  name="flexRadioDefault"
                  id="flexRadioDefault2"
                  defaultChecked
                />
                <label className="form-check-label" htmlFor="flexRadioDefault2">
                  Chưa thanh toán
                </label>
              </div>
            </div>
            <Input
              id="txtTotalPrice"
              type="number"
              lable="Tổng tiền"
              maxLength="100"
              readonly="true"
              frmField={formik.getFieldProps("totalPrice")}
              err={formik.touched.totalPrice && formik.errors.totalPrice}
              errMessage={formik.errors.totalPrice}
            ></Input>
            <select
              className="form-control mb-4"
              id="customer"
              frmField={formik.getFieldProps("customer")}
              err={formik.touched.customer && formik.errors.customer}
              errMessage={formik.errors.customer}
            >
              <option value="customer">Khách hàng</option>
            </select>
            <select
              className="form-control mb-4"
              id="staff"
              frmField={formik.getFieldProps("staff")}
              err={formik.touched.staff && formik.errors.staff}
              errMessage={formik.errors.staff}
            >
              <option value="staff">Nhân viên</option>
            </select>
            <select
              className="form-control mb-4"
              id="table"
              frmField={formik.getFieldProps("table")}
              err={formik.touched.table && formik.errors.table}
              errMessage={formik.errors.table}
            >
              <option value="table">Bàn</option>
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

export default Order;
