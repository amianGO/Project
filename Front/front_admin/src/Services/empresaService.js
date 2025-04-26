import axiosInstance from "./axiosInstace";

const EmpresaService = {
    getAllUsers: async (_id) => {
        try {
            const { data } = await axiosInstance.get(`/empresa/list/${_id}/users`);
            console.log("Usuarios Obtenidos: ",  data);
            return data;
        } catch (error) {
            console.error(error);
            throw error;
        }
    }
}

export default EmpresaService;