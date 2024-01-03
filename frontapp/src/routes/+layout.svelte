<script>
    import '../app.css';

    // 왜 여기서 import하면 아래 render에 적용이 안되지?
    // import moment from 'moment/min/moment-with-locales'
    // moment.locale('ko')

    const {children} = $props();

    
</script>

<nav class="navbar navbar-expand-lg navbar-light bg-light border-bottom">
    <div class="container">
        <a use:link class="navbar-brand" href="/article/list"><h3>Mock-exam</h3></a>
        <button
            class="navbar-toggler"
            type="button"
            data-bs-toggle="collapse"
            data-bs-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent"
            aria-expanded="false"
            aria-label="Toggle navigation">
            <span class="navbar-toggler-icon" />
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item ">
                    <a use:link class="nav-link" href="/member/join">회원가입</a>
                </li>
                <li class="nav-item">
                    <a use:link class="nav-link" href="/member/login">로그인</a>
                </li>
            </ul>
        </div>
    </div>
</nav>

<div class="container mt-3">
    {@render children()}
</div>