<script>
	import { page } from '$app/stores';
	import { onMount } from 'svelte';
	import moment from 'moment/min/moment-with-locales';
	moment.locale('ko');

	import { browser } from '$app/environment';

	// type Aricle = {
	//             id: String;
	//             title: string;
	//             body: number;
	//             createDate : String;
	//             modifyDate : String;
	//         }

	// let article:Article = [];
	let article = [];
	// 마운트 후에
	onMount(() => {
		// 페이지 매개변수로부터 id 추출
		const id = $page.params['id'];

		fetch('http://localhost:8080/api/v1/articles/' + id)
			.then((response) => response.json())
			.then((json) => (article = json.data.article));
	});

	function modifyArticle() {
		window.location.href = '/article/modify/'+article.id;
	}

	function deleteArticle() {
		// window.location.href = "/article/detail/"+article.id;
		if (confirm('정말로 삭제??')) {
			fetch('http://localhost:8080/api/v1/articles/' + article.id, {
				method: 'DELETE'
			})
				.then((response) => response.json())
				.then((rs) => {
					alert(rs.msg);
					window.location.href = '/article/list';
				});
		}
	}
</script>

<div class="card border-dark mb-2">
	<div class="card-header text-white bg-dark">
		<span>No.{article.id}</span>
		<span class="float-end">작성자</span>
	</div>
	<div class="card-body text-dark">
		<h5 class="card-title">{article.title}</h5>
		<p class="card-text">{article.body}</p>
		<p class="badge bg-light text-dark float-end ">{moment(article.createDate).format('YYYY-MM-DD')}</p>
	</div>
</div>

<a class="btn-sm btn-primary text-decoration-none" href="/article/list">목록으로</a>
<div class="float-end">
	<a class="btn-sm btn-success text-decoration-none" href="#" onclick={modifyArticle}>수정</a>
	<a class="btn-sm btn-danger text-decoration-none" href="#" onclick={deleteArticle}>삭제</a>
</div>