* project-name: dictionary
* virtual-phone: medium phone - android 11
* Translate API: 
	* Yandex traslate - get meaning
		* account: vvthienpersonal@gmail.com
		* fund : 75$
		* limit charater: 5.000.000 characters or 75$
		* requirement:
			* host: 
			* hearder: https://translate.yandex.net/api/v1.5/tr.json/translate
			* Content-Type : application/x-www-form-urlencoded
			* body: (type : x-www-form-urlencoded)
				* key : <ask me for api-key>
				* lang: en-vi or vi-en
				* text: <text want to translate>
				* options: <option>
				* callback: <option>
			* respose:
				{
    					"code": 200,
    					"lang": "vi-en",
    					"text": [
    					    "Hello, My Name is thien."
    					]
				}
	* Free Dictionary API - get type of word (just for english to vietnamese)
		* host: https://api.dictionaryapi.dev/api/v2/entries/en/<word>
		* response: update

* period-01: draw layout
	* sign in (hoangnguyen)
	* sign up (hoangnguyen)
	* password forgetfulness (thien)
	* home page (dung)
	* word translation (hieu)
	* text translation (hieu)
	* marked words (vunguyen)
	* history (vunguyen)
	* information (dung)
	* setting (thien)

	* branchs:
		* hoangnguyen-layout
		* thienvu-layout
		* vunguyen-layout
		* dung-layout
		* hieu-layout
	* flow: draw layout -> push to your branch -> open merge request to layout branch -> leave it for me
		if ok -> i will delete layout branch and create new branch for you to code new fearture



