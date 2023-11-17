import './App.css';
import { useState, useEffect } from 'react';
import { Button, Table, Form } from 'react-bootstrap';
import { HOST, PORT } from './constant'
import { useParams } from 'react-router-dom';
export const CertiShow = () => {
    const { id } = useParams();
    const [certi, setCerti] = useState(null)
    const [searchFirstTime, setsearchFirstTime] = useState(false)
    const [isValidCerti, setIsValidCerti] = useState(false)
    const [messageCerti, setMessageCerti] = useState("")


    useEffect(() => {
        fetchCerti(id)
    }, [])

    const fetchCerti = (id) => {
        var requestOptions = {
            method: 'GET',
            redirect: 'follow'
        };

        fetch(`http://${HOST}:${PORT}/certificate/` + id, requestOptions)
            .then(response => response.text())
            .then(result => {
                result = JSON.parse(result)
                if (searchFirstTime === false) {
                    setsearchFirstTime(true)
                }

                setCerti(result)
                getMessageCerti(id)
                verifyCerti(id)
            })
            .catch(error => console.log('error', error));
    }

    const verifyCerti = (id) => {
        var requestOptions = {
            method: 'GET',
            redirect: 'follow'
        };

        fetch(`http://${HOST}:${PORT}/certificate/verify/${id}`, requestOptions)
            .then(response => response.text())
            .then(result => {
                if (result == "true") {
                    setIsValidCerti(true)
                } else {
                    setIsValidCerti(false)
                }

            })
            .catch(error => console.log('error', error));



    }

    const getMessageCerti = (id) => {
        var requestOptions = {
            method: 'GET',
            redirect: 'follow'
        };

        fetch(`http://${HOST}:${PORT}/certificate/message/${id}`, requestOptions)
            .then(response => response.text())
            .then(result => {
                console.log(result)
                setMessageCerti(result)
            })
            .catch(error => console.log('error', error));
    }
    

    return (
        <div className="App" id="content">

            <Form>
                <Form.Group controlId="formBasicEmail">
                    <br />
                    {certi != null && <Table className="margin-top" striped bordered hover variant="dark">
                        <thead>
                            <tr>
                                <th>Informações sobre o diploma</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td>ID</td>
                                <td>{certi.id}</td>
                            </tr>
                            <tr>
                                <td>Nome</td>
                                <td>{certi.name}</td>
                            </tr>
                            <tr>
                                <td>Gênero</td>
                                <td>{certi.gender}</td>
                            </tr>
                            <tr>
                                <td>Data de nascimento</td>
                                <td>{certi.birthday}</td>
                            </tr>
                            <tr>
                                <td>Data de conclusão</td>
                                <td>{certi.completeday}</td>
                            </tr>
                            <tr>
                                <td>Curso</td>
                                <td>{certi.spec}</td>
                            </tr>
                            <tr>
                                <td>Nota</td>
                                <td>{certi.grade}</td>
                            </tr>
                            <tr>
                                <td>ToString</td>
                                <td>
                                    {certi.signature && messageCerti.substring(0, 50) + " ..."}
                                    <br />
                                    {certi.signature && <Button className="margin-top" onClick={() => { navigator.clipboard.writeText(messageCerti) }} variant="success">
                                        Copiar
                                    </Button>}
                                </td>
                            </tr>

                            <tr>
                                <td>Confirmação da assinatura</td>
                                <td>
                                    {certi.signature == null ? "Não assinado" : certi.signature.substring(0, 50) + " ..."}
                                    <br />
                                    {certi.signature && <Button className="margin-top" onClick={() => { navigator.clipboard.writeText(certi.signature) }} variant="success">
                                        Copiar
                                    </Button>}
                                </td>
                            </tr>

                            <tr>
                                <td>Status</td>
                                <td>
                                    {certi.signature == null && "Não assinado"}
                                    {certi.signature && (isValidCerti == true ? "Válido" : "Ilegal")}
                                </td>
                            </tr>
                        </tbody>
                    </Table>}

                    {searchFirstTime && certi == null && <Form.Label>Diploma não existe</Form.Label>}


                </Form.Group>
            </Form>
        </div>
    );
}

