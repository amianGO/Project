import React from "react";
import { NavLink } from "react-router-dom";
import { Navigate } from "react-router-dom";


const HomePage = () => {
    const handleLogout = () => {
        localStorage.removeItem("auth");
        localStorage.removeItem("empresa");
        <Navigate to="/" replace/>;

    }

    return (
        <div>
            <nav style={{marginBottom: "1rem"}}>
                <NavLink to="/login" style={{marginRight: "1rem"}}>Login</NavLink>
                <NavLink to="/register" style={{marginRight: "1rem"}}>Register</NavLink>
                <button onClick={handleLogout}>Cerrar Sesion</button>
            </nav>
        </div>
    )

    
}

export default HomePage;