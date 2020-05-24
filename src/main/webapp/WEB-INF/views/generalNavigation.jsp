        <nav class="navbar navbar-expand-lg navbar-dark" style="background-color:rgb(63, 70, 173);">
            <a class="navbar-brand" href="#"><i class="fab fa-stripe-s"></i>crumvy</a>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
                    aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>

            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="navbar-nav mr-auto">
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/">Home<span class="sr-only"></span></a>
                    </li>
                    <li class="nav-item">
                        <a href="${pageContext.request.contextPath}/goPremium" class="nav-link">Pricing</a>
                    </li>
                    <li class="nav-item">
                        <a href="${pageContext.request.contextPath}/aboutUs" class="nav-link">About Us</a>
                    </li>
                </ul>
                <form:form action="${pageContext.request.contextPath}/logout" method="POST"
                           class="form-inline my-2 my-lg-0">
                    <input type="submit" value="&#xf2f5Logout" class=" btn btn-outline-info my-2 my-sm-0">
                </form:form>
            </div>
        </nav>