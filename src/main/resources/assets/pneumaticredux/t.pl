use String::CamelCase qw(decamelize);

while(<>) {
	s/"(.+?)"/'"' . decamelize($1) . '"'/ge;
	print;
}
