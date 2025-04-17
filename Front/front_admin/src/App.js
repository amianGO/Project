import Register from './Components/Register';
import Login from './Components/Login';
import Usuarios from './Pages/Usuarios';
import PrivateRoute from './Components/PrivateRoute';
import {Routes, Route, NavLink, BrowserRouter } from 'react-router-dom';
import HomePage from './Pages/Home';

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path='/' element= {<HomePage/>}/>
        <Route path='/login' element ={<Login/>}/>
        <Route path='/register' element={<Register/>}/>

        <Route path='/usuarios' element={
          <PrivateRoute>
          <Usuarios />
          </PrivateRoute>
        } />

      </Routes>
      </BrowserRouter>  
    
  );
}

export default App;
