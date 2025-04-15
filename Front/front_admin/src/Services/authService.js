import axiosInstance from "./axiosInstace";

const authService = { //Esto es un Objeto que contiene funciones, Encargado de hacer peticiones a la API
    
    Create: async (userData) => { //Esta funcion es la encargada de crear un nuevo usuaro
        try {
            const response = await axiosInstance.post("/auth/register", userData);
            return response.data; 
        } catch (error) {
            console.error(error);
            throw error;
        }
    },

    Login: async (credentials) => { //Esta funcion es la Encargada de Iniciar sesion con el usuario Existente
      try {
        const response = await axiosInstance.post("/auth/login", credentials);        
        return response.data;
      } catch (error) {
        console.error(error);
        throw error;
      }  
    }
};

export default authService;