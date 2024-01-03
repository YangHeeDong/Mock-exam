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

        form.passwordConfirm.value = form.passwordConfirm.value.trim();

        if (form.passwordConfirm.value.length === 0) {
            alert('passwordConfirm is required');
            form.passwordConfirm.focus();
            return;
        }

        form.email.value = form.email.value.trim();

        if (form.email.value.length === 0) {
            alert('email is required');
            form.email.focus();
            return;
        }

        if(form.password.value != form.passwordConfirm.value){
            alert('Password and password confirm do not match.');
            form.password.focus();
            return;
        }

        fetch('http://localhost:8080/api/v1/member/join', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                loginId: form.loginId.value,
                password: form.password.value,
                passwordConfirm : form.passwordConfirm.value,
                email : form.email.value
            })
        })
        .then(response => response.json())
        .then(rs => {
            // 회원가입 결과
            window.location.href = "/member/login";
        })
        .catch(error => {
            console.error(error);
        });
    }
</script>

<h3>Join</h3>
<form on:submit|preventDefault={submitLoginForm} class="">
    <div class="input-group">
        <div class="row">

        </div>
        <span class="col-sm-2 input-group-text bg-dark text-white border-dark">Login Id</span>
        <input type="col-sm-10 text" name="loginId" class="form-control border-dark" placeholder="Enter Login Id" aria-describedby="basic-addon1">
    </div>

    <div class="input-group mt-2">
        <span class="input-group-text bg-dark text-white border-dark">Password</span>
        <input type="password" name="password" class="form-control border-dark" placeholder="Enter Password" aria-describedby="basic-addon1">
    </div>
    <div class="input-group mt-2">
        <span class="input-group-text bg-dark text-white border-dark">Password Confirm</span>
        <input type="password" name="passwordConfirm" class="form-control border-dark" placeholder="Enter PasswordConfirm" aria-describedby="basic-addon1">
    </div>
    <div class="input-group mt-2">
        <span class="input-group-text bg-dark text-white border-dark">Email</span>
        <input type="email" name="email" class="form-control border-dark" placeholder="Enter Email" aria-describedby="basic-addon1">
    </div>

    <button type="submit" class="btn-sm bg-dark text-white mt-2 text-decoration-none float-end ">회원가입</button>

</form>