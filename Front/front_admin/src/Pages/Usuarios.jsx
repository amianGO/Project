import React from "react";

const Usuarios = () => {

    const storedEmpresa = localStorage.getItem("empresa");
    const empresa = storedEmpresa ? JSON.parse(storedEmpresa) : null;

    return(
        
        <div>
            <h1>{empresa?.name ?? "Nombre no Disponible"}</h1>
            <p>Aqui Veremos los Usuarios de la Empresa</p>
        </div>
        

    )
}

export default Usuarios;