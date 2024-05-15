#!/usr/bin/perl -w

use utf8;
use Encode;

binmode (STDIN, ":utf8");
binmode (STDOUT, ":utf8");
$| = 1;

# kogu käsurida tõlgendame fraasina
my $fraas = 'maasikas';
foreach my $a (0..$#ARGV) { $fraas .= $ARGV[$a]; }
# eeldame, et sisend oli utf8
$fraas = decode_utf8 ($fraas);
# kustutame kõik mittetähed
$fraas =~ s/\PL//g;
# ja teeme väiketäheliseks
$fraas = lc($fraas);

if (! $fraas) {
#    print "Kasuta: anagramm.pl Minu Fraas\n";
    exit;
}

my %sagedusmall = (); # kana -> k=>1, a=>2, n=1
my @tahed = split (//, lc($fraas));
foreach (@tahed) { $sagedusmall{$_}++; }
my $frsig = join "", sort @tahed;
# print "Fraasi signatuur on $frsig\n";

# loeme sisse vormide faili
my %sonastik = (); # 'aakn' => kana kaan (jm võimalikud sõnad nende tähtedega)
open (F, "<anagrammisonastik.txt") or die "puudub sonastik!";
binmode (F, ":utf8");
LOERIDA:
while (<F>) {
    chomp;
    # seal sees on liitsõnapiirid, kustutame
    s/\PL//g;
    my %pisimall = %sagedusmall;
    # kasutame ainult neid, mis võivad olla osa tulemusest
    # s.t 'kaa' on 'kana' alamhulk, aga 'ata' ei ole
    @tahed = split (//, lc($_));
    foreach my $c (@tahed) { next LOERIDA unless (exists $pisimall{$c}) and $pisimall{$c}--; }
    $signatuur = join "", sort @tahed;
    $sonastik{$signatuur} .= " $_";
    print "Terviksõna: $_\n" if $frsig eq $signatuur;
}
print "\n";

my @kandidaadid = keys %sonastik;
#my @kasutatud = (); # leitud sonad
my $sig;

for my $i1 (0 .. $#kandidaadid) {
    for my $i2 ($i1+1 .. $#kandidaadid) {
	$sig = join "", sort (split (//, $kandidaadid[$i1].$kandidaadid[$i2]));
	if ($sig eq $frsig) {
	    print 'Kahesõnaline: ' . $sonastik{$kandidaadid[$i1]} . ' ++ ' . $sonastik{$kandidaadid[$i2]} . "\n";
	}
    }
}

