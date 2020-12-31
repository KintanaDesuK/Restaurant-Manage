import React, { useState, Fragment, useEffect } from "react";
import { Button, Modal } from "react-bootstrap";
import Input from "../controll/input";
import { useFormik, yupToFormErrors } from "formik";
import * as Yup from "yup";
const Dish = (props) => {
  const formik = useFormik({
    initialValues: {
      dishName: "",
      dishPrice: "",
      status: "",
      onsale: "",
      dishCategory: "",
    },
    validationSchema: Yup.object({
      dishName: Yup.string()
        .required("Require")
        .min(5, "Must be 5 characters or more"),
      dishPrice: Yup.string().required("Require"),
      status: Yup.string()
        .required("Require")
        .min(5, "Must be 5 characters or more"),
      onsale: Yup.string().required("Require"),
      dishCategory: Yup.string()
        .required("Require")
        .min(5, "Must be 5 characters or more"),
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
                <h3 className="card-title">Món ăn</h3>
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
                    <th>Tên món ăn</th>
                    <th>Giá</th>
                    <th>Khuyến mãi</th>
                    <th>Trạng thái</th>
                    <th>Ảnh</th>
                    <th>Loại món ăn</th>
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
              id="txtDishName"
              type="text"
              lable="Tên món ăn"
              maxLength="100"
              frmField={formik.getFieldProps("dishName")}
              err={formik.touched.dishName && formik.errors.dishName}
              errMessage={formik.errors.dishName}
            ></Input>
            <Input
              id="txtDishPrice"
              type="number"
              lable="Giá"
              maxLength="100"
              frmField={formik.getFieldProps("dishPrice")}
              err={formik.touched.dishPrice && formik.errors.dishPrice}
              errMessage={formik.errors.dishPrice}
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
            <Input
              id="txtStatus"
              type="text"
              lable="Trạng thái"
              maxLength="100"
              frmField={formik.getFieldProps("status")}
              err={formik.touched.status && formik.errors.status}
              errMessage={formik.errors.status}
            ></Input>
            <Input
              type="file"
              lable="Ảnh"
              id="fileAvatar"
              frmField={formik.getFieldProps("avatar")}
              err={formik.touched.avatar && formik.errors.avatar}
              errMessage={formik.errors.avatar}
            />
            <select
              className="form-control mb-4"
              id="dishCategory"
              frmField={formik.getFieldProps("dishCategory")}
              err={formik.touched.dishCategory && formik.errors.dishCategory}
              errMessage={formik.errors.dishCategory}
            >
              <option value="dishCategory">Danh mục món ăn</option>
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

export default Dish;
