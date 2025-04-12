import { useState } from "react";
import authService from "../Services/authService";

const Login  = () => {                                                      //Componente de Login
    const [credentials, setCredentials] = useState({                        //Estado para guardar las credenciales
        email:"",
        password: "",
    });

    const [menssage, setMessage] = useState("");

    const handleChange = (e) => {                                           //Actualizar el estado de los inputs
        setCredentials({
            ...credentials,                                                 //Con el spread operator se copian los valores actuales
            [e.target.name]: e.target.value,                                //El nombre del input se convierte en la propiedad del objeto
        });
    };

    const handleLogin = async (e) => {                                      //Funcion para manejar el login
        e.preventDefault();                                                 //Evitar que se recargue la pagina
        try {
            const response = await authService.Login(credentials);          //Llamar al servicio de autenticacion
            console.log("Login Exitoso: ", response);
            setMessage("Inicio de Sesion Exitoso");
            //Aqui guardaremos el Token, redirigir, etc.
        } catch (error) {
            console.error("Error al Iniciar Sesion: ", error);
            setMessage("Credenciales Incorrectas");
        }
    };

    return (
        <div style={{maxWidth: "400px", margin: "auto", padding: "1rem"}}>
            <h2>Iniciar Sesion</h2>
            <form onSubmit={handleLogin}>
                <div>
                    <label>Email: </label>
                    <input 
                    type="email"
                    name="email"
                    value={credentials.email}
                    onChange={handleChange}
                    required
                    />
                </div>
                <div style={{marginTop: "0.6rem"}}>
                    <label>Contraseña: </label>
                    <input 
                    type="password"
                    name="password"
                    value={credentials.password}
                    onChange={handleChange}
                    required
                    />
                </div>
                <button type="submit" style={{marginTop: "1rem"}}>Iniciar Sesion</button>
            </form>
            {menssage && <p>{menssage}</p>}
        </div>
    );
};

export default Login;