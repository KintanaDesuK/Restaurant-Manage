import React, { Component } from "react";
import "./sa.css";
import { Link } from "react-router-dom/cjs/react-router-dom.min";
class LeftSite extends Component {
  state = {};
  render() {
    return (
      <div>
        <ul
          className="navbar-nav bg-gradient-primary sidebar sidebar-dark accordion"
          id="accordionSidebar"
        >
          {/* Sidebar - Brand */}
          <Link
            className="sidebar-brand d-flex align-items-center justify-content-center"
            to="Trang chủ"
          >
            <div className="sidebar-brand-icon rotate-n-15">
              <i className="fas fa-laugh-wink" />
            </div>
            <div className="sidebar-brand-text mx-3">Nhà hàng F4</div>
          </Link>
          {/* Divider */}
          <hr className="sidebar-divider my-0" />
          {/* Nav Item - Dashboard */}
          <li className="nav-item active">
            <Link className="nav-link" to="home">
              <i className="fas fa-fw fa-tachometer-alt" />
              <span>Trang chủ</span>
            </Link>
          </li>
          {/* Divider */}
          <hr className="sidebar-divider" />
          {/* Heading */}
          <div className="sidebar-heading">Interface</div>
          {/* Nav Item - Pages Collapse Menu */}
          <li className="nav-item">
            <Link
              className="nav-link collapsed"
              data-toggle="collapse"
              data-target="#collapseTwo"
              aria-expanded="true"
              aria-controls="collapseTwo"
            >
              <i class="fas fa-utensils"></i>
              <span>Quản lý nhà hàng</span>
            </Link>
            <div
              id="collapseTwo"
              className="collapse"
              aria-labelledby="headingTwo"
              data-parent="#accordionSidebar"
            >
              <div className="bg-white py-2 collapse-inner rounded">
                <h6 className="collapse-header">Quản lý món ăn:</h6>
                <Link className="collapse-item" to="dishcategory">
                  Danh mục món ăn
                </Link>
                <Link className="collapse-item" to="dish">
                  Món ăn
                </Link>
                <h6 className="collapse-header">Order Manage:</h6>
                <Link className="collapse-item" to="order">
                  Đơn hàng
                </Link>
                <Link className="collapse-item" to="orderdetails">
                  Chi tiết đơn hàng
                </Link>

                <h6 className="collapse-header">Area Manage:</h6>
                <Link className="collapse-item" to="area">
                  Khu vực
                </Link>
                <Link className="collapse-item" to="table">
                  Bàn
                </Link>
              </div>
            </div>
          </li>
          {/* Nav Item - Utilities Collapse Menu */}
          <li className="nav-item">
            <Link
              className="nav-link collapsed"
              data-toggle="collapse"
              data-target="#collapseUtilities"
              aria-expanded="true"
              aria-controls="collapseUtilities"
            >
              <i class="fas fa-warehouse"></i>
              <span>Quản lý kho</span>
            </Link>
            <div
              id="collapseUtilities"
              className="collapse"
              aria-labelledby="headingUtilities"
              data-parent="#accordionSidebar"
            >
              <div className="bg-white py-2 collapse-inner rounded">
                <h6 className="collapse-header">Quản lý nguyên liệu:</h6>
                <Link className="collapse-item" to="ingredient">
                  Nguyên liệu
                </Link>
                <Link className="collapse-item" to="ingredientcategory">
                  Loại nguyên liệu
                </Link>
              </div>
            </div>
          </li>
          {/* Divider */}
          <hr className="sidebar-divider" />
          {/* Heading */}
          <div className="sidebar-heading">Addons</div>
          {/* Nav Item - Pages Collapse Menu */}
          <li className="nav-item">
            <Link
              className="nav-link collapsed"
              data-toggle="collapse"
              data-target="#collapsePages"
              aria-expanded="true"
              aria-controls="collapsePages"
            >
              <i class="fas fa-user-alt"></i>
              <span>Quản lý người dùng</span>
            </Link>
            <div
              id="collapsePages"
              className="collapse"
              aria-labelledby="headingPages"
              data-parent="#accordionSidebar"
            >
              <div className="bg-white py-2 collapse-inner rounded">
                <h6 className="collapse-header">Quản lý khách hàng:</h6>
                <Link className="collapse-item" to="customer">
                  Khách hàng
                </Link>
                <h6 className="collapse-header">Quản lý nhân viên:</h6>
                <Link className="collapse-item" to="staff">
                  Nhân viên
                </Link>
              </div>
            </div>
          </li>
          {/* Nav Item - Charts */}
          <li className="nav-item">
            <Link className="nav-link" to="revenue">
              <i class="fas fa-coins"></i>
              <span>Thống kê doanh thu</span>
            </Link>
          </li>
        </ul>
      </div>
    );
  }
}

export default LeftSite;
