import React, { useState, Fragment, useEffect } from "react";
import { Button, Modal } from "react-bootstrap";
import Input from "../controll/input";
import { useFormik, yupToFormErrors } from "formik";
import * as Yup from "yup";
const Staff = (props) => {
  const formik = useFormik({
    initialValues: {
      fullName: "",
      address: "",
      phoneNum: "",
      email: "",
      gender: "",
      role: "",
      status: "",
    },
    validationSchema: Yup.object({
      fullName: Yup.string().required("Require"),
      address: Yup.string().required("Require"),
      phoneNum: Yup.string().required("Require"),
      email: Yup.string().required("Require").email(),
      gender: Yup.string().required("Require"),
      role: Yup.string().required("Require"),
      status: Yup.string().required("Require"),
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
                <h3 className="card-title">Nhân viên</h3>
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
                    <th>Họ tên nhân viên</th>
                    <th>Địa chỉ</th>
                    <th>Giới tính</th>
                    <th>Số điện thoại</th>
                    <th>Email</th>
                    <th>Ảnh</th>
                    <th>Quyền</th>
                    <th>Trạng thái</th>
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
          <Modal.Title>Nhân viên</Modal.Title>
        </Modal.Header>
        <form onSubmit={formik.handleSubmit}>
          <Modal.Body>
            <Input
              id="txtFullName"
              type="text"
              lable="Họ tên nhân viên"
              maxLength="100"
              frmField={formik.getFieldProps("fullName")}
              err={formik.touched.fullName && formik.errors.fullName}
              errMessage={formik.errors.fullName}
            ></Input>
            <Input
              id="txtAddress"
              type="text"
              lable="Địa chỉ"
              maxLength="200"
              frmField={formik.getFieldProps("address")}
              err={formik.touched.address && formik.errors.address}
              errMessage={formik.errors.address}
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
                  Nam
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
                  Nữ
                </label>
              </div>
            </div>
            <Input
              id="txtPhoneNum"
              type="number"
              lable="Số điện thoại"
              maxLength="15"
              frmField={formik.getFieldProps("phoneNum")}
              err={formik.touched.phoneNum && formik.errors.phoneNum}
              errMessage={formik.errors.phoneNum}
            ></Input>
            <Input
              id="txtEmail"
              type="text"
              lable="Email"
              maxLength="100"
              frmField={formik.getFieldProps("email")}
              err={formik.touched.email && formik.errors.email}
              errMessage={formik.errors.email}
            ></Input>
            <Input
              type="file"
              lable="Ảnh"
              id="fileAvatar"
              frmField={formik.getFieldProps("avatar")}
              err={formik.touched.avatar && formik.errors.avatar}
              errMessage={formik.errors.avatar}
            />
            <Input
              id="txtRole"
              type="text"
              lable="Quyền"
              maxLength="100"
              frmField={formik.getFieldProps("role")}
              err={formik.touched.role && formik.errors.role}
              errMessage={formik.errors.role}
            ></Input>
            <select
              className="form-control mb-4"
              id="status"
              frmField={formik.getFieldProps("status")}
              err={formik.touched.status && formik.errors.status}
              errMessage={formik.errors.status}
            >
              <option value="status">Trạng thái</option>
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

export default Staff;
