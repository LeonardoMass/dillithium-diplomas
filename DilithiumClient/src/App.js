import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import React, { useEffect} from 'react';
import { CertiSearch } from './CertiSearch'
import { CertiCreate } from './CertiCreate'
import { CertiAdmin } from './CertiAdmin'
import { CertiHome } from './CertiHome'
import { CertiSign } from './CertiSign'
import { CertiShow } from './CertiShow'
import { Navbar, Nav, Container, Carousel } from 'react-bootstrap'
import { HOST, PORT } from './constant'
const App = () => {

  return (

    <Router>
      <Navbar bg="dark" variant="dark" expand="lg">
        <Container>
          <Navbar.Brand href="/home">Página inicial</Navbar.Brand>
          <Navbar.Toggle aria-controls="basic-navbar-nav" />
          <Navbar.Collapse id="basic-navbar-nav">
            <Nav className="me-auto">
              <Nav.Link href="/search">Procurar diploma por id</Nav.Link>
              <Nav.Link href="/create">Criar diploma</Nav.Link>
              <Nav.Link href="/admin">Gerenciar diplomas</Nav.Link>
              <Nav.Link href="/sign">Assinar documento</Nav.Link>
            </Nav>
          </Navbar.Collapse>
        </Container>
      </Navbar>
      <Routes>

        <Route path="/" element={<CertiHome />} />
        <Route path="/home" element={<CertiHome />} />
        <Route path="/search" element={<CertiSearch />} />
        <Route path="/create" element={<CertiCreate />} />
        <Route path="/admin" element={<CertiAdmin />} />
        <Route path="/sign" element={<CertiSign />} />
        <Route path="/certificate/:id" element={<CertiShow />} />
      </Routes>
      <div className="App"></div>
    </Router >
  );
}
export default App;