var vueApp = new Vue({
	el: '#main-app',
	data: {
		testData: [],
		newData: {},
		message: ''
	},
	methods: {
		loadData: function() {
			this.$http.get('/FileUploadTest/api/test/').then(response => {
				if (response.status == 200) {
					this.testData = response.body;
				} else {
					this.message = 'Error ' + response.status + '!'
				}
			}, response => {
				this.message = 'Error!';
			});
		},
		submitData: function() {
			this.$http.put('/FileUploadTest/api/test/', this.newData).then(response => {
				if (response.status == 200) {
					this.loadData();
				} else {
					this.message = 'Error ' + response.status + '!'
				}
			}, response => {
				this.message = 'Error!';
			});
		},
		uploadData: function() {
			var formData = new FormData();
			formData.append('file', document.getElementById('updFile').files[0]);
			
			this.$http.post('/FileUploadTest/api/test/upload', formData).then(response => {
				if (response.status == 200) {
					this.message = 'Uploaded' + response.body + '!';
				} else {
					this.message = 'Error ' + response.status + '!'
				}
			}, response => {
				this.message = 'Error!';
			});
		}
	}
});

vueApp.loadData();