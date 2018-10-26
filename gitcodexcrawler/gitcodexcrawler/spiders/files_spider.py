import scrapy
from gitcodexcrawler.items import GitcodexcrawlerItem

class FilesSpider(scrapy.Spider):
	name = "files"
	url_github = 'https://github.com'
	start_urls = [
		#'https://github.com/scrapy-plugins/scrapy-splash'
		'https://github.com/search?l=&p=1&q=language%3APython&ref=advsearch&type=Repositories'
	]
	page = 1
	total_pages = 1


	# Github search results page
	def parse(self, response):
		if (self.page <= self.total_pages):
			# Go into each search result
			repository_url_list = response.css('a.v-align-middle').xpath('@href').extract()
			for repository_url in repository_url_list:
				yield scrapy.Request(self.url_github+repository_url, callback=self.directory)
			
			# Crawl next page
			self.page += 1
			yield scrapy.Request(self.start_urls[0].replace('p=1','p='+str(self.page)), callback=self.parse)


	# Directory page inside concrete repository 
	def directory(self, response):
		
		# Each item inside directory
		for item in response.css('table.files tr.js-navigation-item'):
			# Crawl into a directory
			if (item.css('svg.octicon-file-directory')):
				directory_url= self.url_github+item.css('a.js-navigation-open').xpath('@href').extract_first()
				yield scrapy.Request(directory_url, callback=self.directory)
			# Crawl into a file
			else:
				file_url = self.url_github+item.css('a.js-navigation-open').xpath('@href').extract_first()
				yield scrapy.Request(file_url, callback=self.file)
		

	# Just go into 'Raw file page' to get content
	def file(self, response):
		content_url = self.url_github+response.css('div.file-actions a.BtnGroup-item').xpath('@href').extract_first()
		yield scrapy.Request(content_url, callback=self.parse_raw_file)


	# Build item
	def parse_raw_file(self, response):
		file = GitcodexcrawlerItem()
		file['file_url'] = response.url
		file['file_content'] = str(response.css('p ::text').extract_first())
		yield file
