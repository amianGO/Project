import axiosInstance from "./axiosInstace";

const UserService = {

    getAll: async () => {
        try {
            const response = await axiosInstance.get("/users/list");
            return response.data;
        } catch (error) {
            console.error(error);
            throw error;
        }    
    }
}

export default UserService;