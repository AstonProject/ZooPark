<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="fr">

<head>
    <meta charset="utf-8">
    <title>ZooPark</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/global.css">
</head>

<body>
    <div id="body">
        <div id="main-wrapper">
            <header id="main-header">
                <div id="page-header">

                </div>
                <div id="top-menu">
                    <nav>
                        <ul>
                            <li>Argent : 0 Z</li>
                            <li>Employés : 0</li>
                            <li><a href="#">Satisfaction</a></li>
                            <li><a href="#">Messages</a></li>
                            <li>lecture | avance rapide | stop</li>
                            <li>Date</li>
                        </ul>
                    </nav>
                </div>
            </header>
            <div id="main-content">
                <h1>ZooPark</h1>
                <table border="2">
                    <tr>
                        <td><a href="#">Construire</a></td>
                        <td><a href="#">Construire</a></td>
                        <td><a href="#">Construire</a></td>
                        <td><a href="#">Construire</a></td>
                        <td><a href="#">Construire</a></td>
                    </tr>
                    <tr>
                        <td><a href="#">Construire</a></td>
                        <td><a href="#">Construire</a></td>
                        <td><a href="#">Construire</a></td>
                        <td><a href="#">Construire</a></td>
                        <td><a href="#">Construire</a></td>
                    </tr>
                    <tr>
                        <td><a href="#">Construire</a></td>
                        <td><a href="#">Construire</a></td>
                        <td><a href="#">Construire</a></td>
                        <td><a href="#">Construire</a></td>
                        <td><a href="#">Construire</a></td>
                    </tr>
                    <tr>
                        <td><a href="#">Construire</a></td>
                        <td><a href="#">Construire</a></td>
                        <td><a href="#">Construire</a></td>
                        <td><a href="#">Construire</a></td>
                        <td><a href="#">Construire</a></td>
                    </tr>
                    <tr>
                        <td><a href="#">Construire</a></td>
                        <td><a href="#">Construire</a></td>
                        <td><a href="#">Construire</a></td>
                        <td><a href="#">Construire</a></td>
                        <td><a href="#">Construire</a></td>
                    </tr>
                </table>
            </div>
            <footer id="bottom-menu">
                <button name="construction" id="construction">Construction</button>
                <button name="gestion_personnel" id="gestion_personnel">Gestion du personnel</button>
                <button name="consommables" id="consommables">Consommables</button>
                <button name="finances" id="finances">Finances</button>
                <button name="detail_enclos" id="detail_enclos">Détail des Enclos</button>
                <button name="aide" id="aide">Aide</button>
            </footer>
        </div>
    </div>
    <script type="text/javascript" src="${pageContext.request.contextPath}/assets/vendors/jquery-3.1.1.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/assets/scripts/zoo.js"></script>
</body>

</html>