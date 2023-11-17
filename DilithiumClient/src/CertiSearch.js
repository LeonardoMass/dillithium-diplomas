import './App.css';
import { useState } from 'react';
import { Button, Table, Form } from 'react-bootstrap';
import { HOST, PORT } from './constant'
import html2pdf from 'html2pdf.js';
export const CertiSearch = () => {
    const [certi, setCerti] = useState(null)
    const [findingCertiID, setFindingCertiID] = useState(null)
    const [searchFirstTime, setsearchFirstTime] = useState(false)
    const [isValidCerti, setIsValidCerti] = useState(false)
    const [messageCerti, setMessageCerti] = useState("")

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
    function pdf() {
        const content = document.querySelector("#content")
        const options = {
            filename: `diploma-${certi.name}.pdf`,
            html2canvas: { scale: 2 },
            jsPDF: { unit: "mm", format: "a4", orientation: "portrait" }
        }
        html2pdf().set(options).from(content).save();
    }

    function Hyperlink() {
        var hyper = `http://${HOST}:3000/certificate/${certi.id}`;

        return <a href={hyper}> <tr>
            <Button variant="primary" >
                --------- Verificar pelo próprio emissor ---------
            </Button>
        </tr>"</a>;
    }
    return (
        <div className="App">

            <Form>
                <Form.Group controlId="formBasicEmail">
                    <h2>Procurar diploma por id</h2>
                    <Form.Control onChange={e => setFindingCertiID(e.target.value)} placeholder="ID" />
                    <Button className="margin-top" variant="primary" onClick={() => fetchCerti(findingCertiID)}>
                        Procurar
                    </Button>
                    <br />
                    {certi != null && <Table className="margin-top" striped bordered hover variant="dark" id="content">
                        <thead>
                            <tr>
                                <th>Informações sobre o diploma</th>
                                <th><Button onClick={() => pdf()}>Download pdf</Button></th>
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
                                    <br/>
                                    {certi.signature && <Button className="margin-top" onClick={() => { navigator.clipboard.writeText(messageCerti) }} variant="success">
                                        Copiar
                                    </Button>}
                                </td>
                            </tr>

                            <tr>
                                <td>Confirmação da assinatura</td>
                                <td>
                                    {certi.signature == null ? "Não assinado" : certi.signature.substring(0, 50) + " ..."}
                                    <br/>
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
                                    <br /><br />
                                    <Hyperlink />
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

