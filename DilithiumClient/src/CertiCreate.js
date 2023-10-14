import './App.css'
import { useState } from 'react';
import { Button, Form, Col, Row } from 'react-bootstrap';

import {
    HOST, PORT
} from './constant'

export const CertiCreate = () => {
    const [ceftiName, setCertiName] = useState("")
    const [ceftiGender, setCertiGender] = useState("Homem")
    const [ceftiBirthday, setCertiBirthday] = useState("")
    const [ceftiSpec, setCertiSpec] = useState("ADS")
    const [ceftiGrade, setCertiGrade] = useState("Bom")
    const [ceftiCompleteday, setCertiCompleteday] = useState("")
    const createCefti = () => {
        if (ceftiName === "" || ceftiBirthday === "" || ceftiCompleteday === "") {
            window.alert("Por favor insira todas as informações");
            return;
        }


        var myHeaders = new Headers();
        myHeaders.append("Content-Type", "application/json");

        var raw = JSON.stringify({
            "type": "STD",
            "name": ceftiName,
            "gender": ceftiGender,
            "birthday": ceftiBirthday,
            "spec": ceftiSpec,
            "grade": ceftiGrade,
            "completeday": ceftiCompleteday,
            "signature": null,
            "signed": false
        });

        var requestOptions = {
            method: 'POST',
            headers: myHeaders,
            body: raw,
            redirect: 'follow'
        };

        fetch(`http://${HOST}:${PORT}/certificate/`, requestOptions)
            .then(response => response.text())
            .then(result => {
                console.log(result)
                if (result != null) {
                    window.alert("Adicionado novo diploma com sucesso");
                    window.location.reload();
                } else {
                    window.alert("Falha ao adicionar novo diploma");
                }
            })
            .catch(error => console.log('error', error));
    }
    return <div className="App">
        <Form>
            <h2>Criar diploma</h2>
            <Row className="mb-3 margin-top">
                <Form.Group as={Col} controlId="formName">
                    <Form.Label>Nome completo</Form.Label>
                    <Form.Control placeholder="Insira o nome" onChange={e => setCertiName(e.target.value)} />

                </Form.Group>

                <Form.Group as={Col} controlId="formGender">
                    <Form.Label>Gênero</Form.Label>
                    <Form.Select aria-label="Gênero" onChange={e => setCertiGender(e.target.value === 1 ? "Homem" : "Mulher")} >
                        <option value="1">Homem</option>
                        <option value="2">Mulher</option>
                    </Form.Select>
                </Form.Group>
                <Form.Group as={Col} controlId="formBirthday">
                    <Form.Label>Data de nascimento</Form.Label>
                    <Form.Control placeholder="Data de nascimento" onChange={e => setCertiBirthday(e.target.value)} />

                </Form.Group>

            </Row>


            <Row className="mb-3">
                <Form.Group as={Col} controlId="formcompeleteday">
                    <Form.Label>Data de Conclusão</Form.Label>
                    <Form.Control placeholder="Preencha a data da conclusão" onChange={e => setCertiCompleteday(e.target.value)} />

                </Form.Group>

                <Form.Group as={Col} controlId="fromSpec">
                    <Form.Label>Curso</Form.Label>
                    <Form.Select aria-label="Curso" onChange={e => setCertiSpec(e.target.value === 1 ? "ADS" : "CI")} >
                        <option value="1">ADS</option>
                        <option value="2">CI</option>
                    </Form.Select>
                </Form.Group>

                <Form.Group as={Col} controlId="formGrade">
                    <Form.Label>Classificação</Form.Label>
                    <Form.Select aria-label="Classificação" onChange={e => setCertiGrade(e.target.value === 1 ? "Bom" : "Excelente")} >
                        <option value="1">Bom</option>
                        <option value="2">Excelente</option>
                    </Form.Select>
                </Form.Group>
            </Row>
                <Button variant="primary" onClick={() => createCefti()}>
                    Salvar
                </Button>
        </Form>


    </div>



}