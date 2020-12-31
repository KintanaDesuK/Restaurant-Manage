import React, { Component } from "react";
import "./sa.css";

class Footer extends Component {
  state = {};
  render() {
    return (
      <div>
        <footer className="sticky-footer bg-white">
          <div className="container my-auto">
            <div className="copyright text-center my-auto">
              <span>Copyright © Your Website 2019</span>
            </div>
          </div>
        </footer>
      </div>
    );
  }
}

export default Footer;
