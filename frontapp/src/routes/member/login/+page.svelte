<script lang="ts">
    
    import { browser } from '$app/environment';

    function submitLoginForm(this: HTMLFormElement) {
        const form: HTMLFormElement = this;

        form.loginId.value = form.loginId.value.trim();
        
        if (form.loginId.value.length === 0) {
            alert('loginId is required');
            form.loginId.focus();
            return;
        }

        form.password.value = form.password.value.trim();

        if (form.password.value.length === 0) {
            alert('password is required');
            form.password.focus();
            return;
        }

        fetch('http://localhost:8080/api/v1/member/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                loginId: form.loginId.value,
                password: form.password.value
            })
        })
        .then(response => response.json())
        .then(rs => {
            // 로그인결과
            // window.location.href = "/article/detail/"+rs.data.article.id;
        })
        .catch(error => {
            console.error(error);
        });
    }
</script>

<h3>Login</h3>
<form on:submit|preventDefault={submitLoginForm}>
    <div class="input-group">
        <span class="input-group-text bg-dark text-white border-dark">Login Id</span>
        <input type="text" name="loginId" class="form-control border-dark" placeholder="Enter Login Id" aria-describedby="basic-addon1">
    </div>

    <div class="input-group mt-2">
        <span class="input-group-text bg-dark text-white border-dark">Password</span>
        <input type="password" name="password" class="form-control border-dark" placeholder="Enter Password" aria-describedby="basic-addon1">
    </div>

    <button type="submit" class="btn-sm bg-dark text-white mt-2 text-decoration-none float-end ">로그인</button>

</form>
