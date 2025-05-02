import axiosInstance from "./axiosInstace";

const EmpresaService = {
    getAllUsers: async (id) => {
        try {
            const token = localStorage.getItem("token");
            if (!token) throw new Error("Token no disponible");
            const response = await axiosInstance.get(`/empresa/list/${id}/users`);

            console.log("Usuarios Obtenidos: ",  response.data);
            return response.data;
        } catch (error) {
            console.error(error);
            throw error;
        }
    }
}

export default EmpresaService;