    import axios from "axios";

    const axiosInstance = axios.create({
        baseURL: process.env.REACT_APP_API_URL,
        timeout: 10000,
        headers:{
            'Content-Type':'application/json',
        },
    });

    //Agregar Token a cada request
    axiosInstance.interceptors.request.use(
        (config) => {
        const token = localStorage.getItem("token");
        if (token) {
            config.headers.Authorization = `Bearer ${token}`;
        }
        return config;
    }, (error) => {
        return Promise.reject(error);
    })

    export default axiosInstance;