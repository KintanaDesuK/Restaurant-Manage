import axios from "axios";

const url = {
  baseUrl: "https://www.saigontech.edu.vn/restful-api",
  login: "/login",
  majors: "/majors",
};

const instance = axios.create({
  baseURL: url.baseUrl,
  headers: {
    "Content-Type": "application/json",
    Accept: "application/json",
  },
});

const api = {
  url,
  instance,
  get: instance.get,
  post: instance.post,
  put: instance.put,
  delete: instance.delete,
};

export default api;
