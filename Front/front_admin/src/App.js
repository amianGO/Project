import Register from './Components/Register';
import Login from './Components/Login';
import { Routes, Route, NavLink } from 'react-router-dom';

function App() {
  return (
    <div className='App'>
      <h1>
        Inicio de Sesion
      </h1>
      <nav style={{marginBottom: "1rem"}}>
        <NavLink to="/login" style={{marginRight: "1rem"}}>Login</NavLink>
        <NavLink to="/register" style={{marginRight: "1rem"}}>Register</NavLink>
      </nav>
      <Routes>
        <Route path='/login' element ={<Login/>}/>
        <Route path='/register' element={<Register/>}/>
      </Routes>
    </div>
  );
}

export default App;
