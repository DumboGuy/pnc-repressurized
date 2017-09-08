#! /usr/bin/perl -w
    eval 'exec /usr/bin/perl -S $0 ${1+"$@"}'
        if 0; #$running_under_some_shell

use strict;
use File::Find ();

# Set the variable $File::Find::dont_use_nlink if you're using AFS,
# since AFS cheats.

# for the convenience of &wanted calls, including -eval statements:
use vars qw/*name *dir *prune/;
*name   = *File::Find::name;
*dir    = *File::Find::dir;
*prune  = *File::Find::prune;

sub wanted;



# Traverse desired filesystems
File::Find::find({wanted => \&wanted}, '.');
exit;


sub wanted {
    /^.*\.(ogg|png|json|obj|tcn|mtl)\z/s
    && process();
}

sub process {
	my $name2 = $name;
	$name2 =~ s/([A-Z]+)/_\L$1/g;
	$name2 =~ s/^_//;
	$name2 =~ s!/_!/!g;
	print "mv $name $name2\n" if $name ne $name2;
}

