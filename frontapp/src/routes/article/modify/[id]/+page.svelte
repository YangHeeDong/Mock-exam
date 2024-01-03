<script lang="ts">
	import { page } from '$app/stores';
	import { onMount } from 'svelte';
	import { browser } from '$app/environment';
	import { fromJSON } from 'postcss';

	let article = [];

	// 마운트 후에
	onMount(() => {
		// 페이지 매개변수로부터 id 추출
		const id = $page.params['id'];

		fetch('http://localhost:8080/api/v1/articles/' + id)
			.then((response) => response.json())
			.then((json) => (article = json.data.article));
	});

	function submitLoginForm(this: HTMLFormElement) {
			const form: HTMLFormElement = this;

			form.title.value = form.title.value.trim();

			if (form.title.value.length === 0) {
				alert('title is required');
				form.title.focus();
				return;
			}

			form.body.value = form.body.value.trim();

			if (form.body.value.length === 0) {
				alert('body is required');
				form.body.focus();
				return;
			}

			fetch('http://localhost:8080/api/v1/articles', {
				method: 'PUT',
				headers: {
					'Content-Type': 'application/json'
				},
				body: JSON.stringify({
					id: article.id,
					title: form.title.value,
					body: form.body.value
				})
			})
			.then((response) => response.json())
			.then((rs) => {
				alert(rs.msg);
				window.location.href = '/article/detail/' + rs.data.article.id;
			})
			.catch((error) => {
				console.error(error);
			});
		}
</script>

<h3>Modify Article</h3>
<form on:submit|preventDefault={submitLoginForm}>
	<div class="input-group">
		<span class="input-group-text bg-dark text-white border-dark">Title</span>
		<input
			type="text"
			name="title"
			class="form-control border-dark"
			placeholder="Enter title"
			value={article.title}
			aria-describedby="basic-addon1"
		/>
	</div>

	<div class="input-group mt-1">
		<span class="input-group-text bg-dark text-white border-dark">body</span>
		<textarea
			name="body"
			class="form-control border-dark"
			aria-label="With textarea"
			rows="20"
			placeholder="Enter body">{article.body}</textarea
		>
	</div>

	<div class="mt-2">
		<a class="btn-sm btn-primary text-decoration-none" href="/article/list">목록으로</a>
		<button type="submit" class="btn-sm bg-dark text-white text-decoration-none float-end">수정</button>
	</div>
</form>
