import React,{useState, useEffect} from "react";
import EmpresaService from "../Services/empresaService";
import {useNavigate } from "react-router-dom";

const Usuarios = () => {

    const storedEmpresa = localStorage.getItem("empresa");
    const empresa = storedEmpresa ? JSON.parse(storedEmpresa) : null;


    const [usuario, setUsuario] = useState([]); //Estado para almacenar la lista de Usuarios
    const [loading, setLoading] = useState(true); //Estado para manejar la carga de datos
    const [error, setError] = useState(null); //Estado para manejar erroress
    const [hasFetched, setHasFetched] = useState(false);
    const navigate = useNavigate();

    useEffect(() => {       //Efecto para cargar los usuarios cuando el componente se monta

        const fetchUsers = async () => {
            try {
                setLoading(true);
                if (!empresa.id || hasFetched) return;

                if (!empresa.id) {
                    navigate("/login");
                }


                const data = await EmpresaService.getAllUsers(empresa?.id);
                console.log("Usuarios Obtenidos: ", data)

                setUsuario(data || []);
            } catch (err) {
                console.error("Error al cargar los usuarioss", err);
                setError("No se pudieron cargar los usuarios, Intenta Nuevamente");
            } finally {
                setLoading(false);
            }

        };
        console.log("Ejecuntando useEffect, Empresa: " , empresa);
        fetchUsers();
        setHasFetched(true)
    },[empresa?.id]);

    if (loading) return <p>Cargando Usuarios</p>;
    if (error) return <p style={{color: "red"}}>{error}</p>
    return(

        <div style={{ padding: "1rem" }}>
        <h2>Usuarios de {empresa?.name ?? "Empresa no disponible"}</h2>
        {usuario.length > 0 ? (
            <ul>
                {usuario.map((user) => (
                    <li key={user.id}>
                        {user.name} - {user.email}
                    </li>
                ))}
            </ul>
        ) : (
            <p>No hay usuarios registrados</p>
        )}
    </div>

    )
}

export default Usuarios;