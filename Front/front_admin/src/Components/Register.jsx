import authService from "../Services/authService";
import React, {useState} from "react";
import { useNavigate } from "react-router-dom";

const Register = () => {                                                        //Es un Componente de Registro

    const navigate = useNavigate();                                             //Esta es una funcion que nos permite navegar entre ruas

    const [Form, setForm] = useState({
        name: "",
        email: "",
        password: "",
    });

    const [error, setError] = useState("");

    const handleChange = (e) => {                                               //Esta funcion se encarga de actualizar el estado del formulario
        setForm({...Form, [e.target.name]: e.target. value});                   //Esto actualiza el estado del formulario con el valor del input que se esta modificando
    };

    const handleSubmit = async (e) => {                                         //Esta funcion se encarga de Enviar el Formulario
        e.preventDefault();                                                     //Esto Evita que la pagina se recargue al enviar el formulario
        try {
            await authService.Create(Form);
            navigate("/login");
        } catch (error) {
            if (error.response && error.response.data) {
                setError(error.response.data);
            } else {
                setError("Hubo un Error al registrarse.");
            }
            
            console.error(error);
        }
    };

    return(
        <div className="register-container">
            <h2>Registrarse</h2>
            <form onSubmit={handleSubmit}>
                <input 
                type="email"
                name="email"
                placeholder="correo"
                value={Form.email}
                onChange={handleChange}
                required 
                />
                <input 
                type="text" 
                name="name"
                placeholder="Nombre"
                value={Form.name}
                onChange={handleChange}
                required 
                />

                <input 
                type="password"
                name="password"
                placeholder="Contraseña"
                value={Form.password}
                onChange={handleChange}
                required 
                />
                <button type="submit" >Registrarse</button>
                {error && <p className="error">{error}</p>}
            </form>
        </div>
    );
};

export default Register;