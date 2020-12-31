import React, { useState, Fragment, useEffect } from "react";
import { Button, Modal } from "react-bootstrap";
import Input from "../controll/input";
import { useFormik, yupToFormErrors } from "formik";
import * as Yup from "yup";
const IngredientCategory = (props) => {
  const formik = useFormik({
    initialValues: {
      ingCategoryName: "",
    },
    validationSchema: Yup.object({
      ingCategoryName: Yup.string().required("Require"),
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
                <h3 className="card-title">Loại nguyên liệu</h3>
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
                    <th>Tên loại nguyên liệu</th>
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
          <Modal.Title>Loại nguyên liệu</Modal.Title>
        </Modal.Header>
        <form onSubmit={formik.handleSubmit}>
          <Modal.Body>
            <Input
              id="txtIngCategoryName"
              type="text"
              lable="Tên loại nguyên liệu"
              maxLength="100"
              frmField={formik.getFieldProps("ingCategoryName")}
              err={
                formik.touched.ingCategoryName && formik.errors.ingCategoryName
              }
              errMessage={formik.errors.ingCategoryName}
            ></Input>
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

export default IngredientCategory;
