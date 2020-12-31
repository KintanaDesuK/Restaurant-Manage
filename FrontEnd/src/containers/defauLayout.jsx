import React, { Component, Fragment } from "react";
import Header from "./header";
import Home from "./../pages/home";
import routes from "./../routes";
import { Switch, Route } from "react-router-dom";
import LeftSite from "./leftSite";
import Footer from "./footer";

class DefauLayout extends Component {
  state = {};
  render() {
    return (
      <Fragment>
        {/* <Header></Header>
        <LeftSite />
        <Switch>
          {routes.map((route, idx) => {
            return route.component ? (
              <Route
                key={idx}
                path={route.path}
                exact={route.exact}
                name={route.name}
                component={route.component}
              />
            ) : null;
          })}
        </Switch>
        <Footer /> */}

        <div>
          <div id="wrapper">
            <LeftSite />
            <div id="content-wrapper" class="d-flex flex-column">
              <div id="content">
                <Header />

                <Switch>
                  {routes.map((route, idx) => {
                    return route.component ? (
                      <Route
                        key={idx}
                        path={route.path}
                        exact={route.exact}
                        name={route.name}
                        component={route.component}
                      />
                    ) : null;
                  })}
                </Switch>
              </div>
              <Footer />
            </div>
          </div>
        </div>
      </Fragment>
    );
  }
}

export default DefauLayout;
