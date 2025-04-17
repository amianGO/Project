import React,{useState, useEffect} from "react";
import UserService from "../Services/userService";
import { Navigate, NavLink, useNavigate } from "react-router-dom";

const Usuarios = () => {

    const storedEmpresa = localStorage.getItem("empresa");
    const empresa = storedEmpresa ? JSON.parse(storedEmpresa) : null;


    const [usuario, setUsuario] = useState([]); //Estado para almacenar la lista de Usuarios
    const [loading, setLoading] = useState(true); //Estado para manejar la carga de datos
    const [error, setError] = useState(null); //Estado para manejar erroress

    const navigate = useNavigate();

    useEffect(() => {       //Efecto para cargar los usuarios cuando el componente se monta

        const fetchUsers = async () => {
            try {
                setLoading(true);
                
                const data = await UserService.getAll();
                console.log(data)
                setUsuario(data);
                setError(null);
            } catch (err) {
                console.error("Error al cargar los usuarioss", err);
                setError("No se pudieron cargar los usuarios, Intenta Nuevamente");
            } finally {
                setLoading(false);
            }

        };

        fetchUsers();
    },[])

    return(

    <div style={{ padding: "1rem" }}>
        <h2>Usuarios de {empresa?.name ?? "Empresa no disponible"}</h2>

        {loading && <p>Cargando usuarios...</p>}
        {error && <p style={{ color: "red" }}>{error}</p>}

        {!loading && !error && (
            <ul>
            {usuario.map(user => (
                <li key={user._id}>
                {user.name} - {user.email}
                </li>
            ))}
            </ul>
        )}
    </div>
        /*
        <div>
            <h1>{empresa?.name ?? "Nombre no Disponible"}</h1>
            <p>Aqui Veremos los Usuarios de la Empresa</p>
        </div>
        */
        

    )
}

export default Usuarios;