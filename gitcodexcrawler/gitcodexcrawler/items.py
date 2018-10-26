# -*- coding: utf-8 -*-

# Define here the models for your scraped items
#
# See documentation in:
# https://doc.scrapy.org/en/latest/topics/items.html

import scrapy


class GitcodexcrawlerItem(scrapy.Item):
	repository_url = scrapy.Field()
	file_name      = scrapy.Field()
	file_content   = scrapy.Field()
	file_url       = scrapy.Field()
